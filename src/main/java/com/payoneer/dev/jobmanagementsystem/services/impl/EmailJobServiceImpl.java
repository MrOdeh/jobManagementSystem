package com.payoneer.dev.jobmanagementsystem.services.impl;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import com.payoneer.dev.jobmanagementsystem.utils.EmailUtil;
import com.payoneer.dev.jobmanagementsystem.utils.Validation;
import com.payoneer.dev.jobmanagementsystem.web.mapper.EmailJobMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailJobServiceImpl implements EmailJobService {

    private final EmailJobRepository emailJobRepository;
    private final Validation validation;
    private final EmailUtil emailUtil;
    private final EmailJobMapper emailJobMapper;

    @Override
    public EmailJob findById(String id) {
        try {
            return emailJobRepository.findOneByJobId(UUID.fromString(id)).orElseThrow(() -> new GenericClientException("Email job not found for# " + id, HttpStatus.BAD_REQUEST ));
        }catch (NullPointerException ne){
            log.error("invalid user input");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<EmailJob> findAll() {
        return emailJobRepository.findAll();
    }

    @Override
    public EmailJob save(EmailJob job, boolean scheduled) {
        // validate E-mail & execution time
        if(!validation.isEmailValid(job.getSender()) || !validation.isEmailValid(job.getReceiver())
        || !validation.isDateValid(job.getExecutionTime())){
            throw new GenericClientException("invalid email Parameters",HttpStatus.BAD_REQUEST);
        }

        // Not scheduled, then the job must be saved and the background service will handle it according to the execution time :)
        if(scheduled){
            return emailJobRepository.save(job);
        }

        // immediate execution
        return emailUtil.sendAndFlush(job);

    }

    @Transactional
    @Override
    public void deleteById(String id) {

        try{
            emailJobRepository.deleteEmailJobByJobId(UUID.fromString(id));
        }catch (NullPointerException ne){
            log.error("Invalid User Input");
        }catch (IllegalArgumentException e){
            log.error("invalid UUID");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<EmailJob> saveAll(List<EmailJob> jobs, boolean schedule) {

        if(jobs == null || jobs.size() == 0){
            throw new GenericClientException("invalid user Input",HttpStatus.BAD_REQUEST);
        }
        jobs.stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue()))
                .forEach(job -> {
                    if(!schedule && (validation.isEmailValid(job.getSender()) && validation.isEmailValid(job.getReceiver()))){
                        emailUtil.sendAndFlush(job); // immediate execution
                    }else if(schedule && (validation.isEmailValid(job.getSender()) && validation.isEmailValid(job.getReceiver())
                            && validation.isDateValid(job.getExecutionTime()))){
                        emailJobRepository.save(job); // scheduled jobs will be handled by event handler
                    }else{
                        job.setJobStatus(JobStatus.FAILED);
                    }});
        return jobs;
    }
}
