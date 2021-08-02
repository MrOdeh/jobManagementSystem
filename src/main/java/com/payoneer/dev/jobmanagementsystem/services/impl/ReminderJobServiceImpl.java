package com.payoneer.dev.jobmanagementsystem.services.impl;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import com.payoneer.dev.jobmanagementsystem.services.ReminderJobService;
import com.payoneer.dev.jobmanagementsystem.utils.ReminderUtil;
import com.payoneer.dev.jobmanagementsystem.utils.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReminderJobServiceImpl implements ReminderJobService {

    private final ReminderJobRepository reminderJobRepository;
    private final Validation validation;
    private final ReminderUtil reminderUtil;

    @Override
    public ReminderJob findById(String id) {
        try {
            return reminderJobRepository.findOneByJobId(UUID.fromString(id)).orElseThrow(() -> new GenericClientException("Email job not found for# " + id, HttpStatus.BAD_REQUEST ));
        }catch (NullPointerException ne){
            log.error("invalid user input");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<ReminderJob> findAll() {
        return reminderJobRepository.findAll();
    }

    @Override
    public ReminderJob save(ReminderJob job, boolean schedule) {
        // validate E-mail & execution time
        if(!validation.isNumberValid(job.getMobileNumber())){
            throw new GenericClientException("invalid Mobile Number", HttpStatus.BAD_REQUEST);
        }

        // if Not immediately then i have to validate the date
        if (schedule && !validation.isDateValid(job.getJobExecutionTime())) {
            throw new GenericClientException("Invalid Date",HttpStatus.BAD_REQUEST);
        }

        // Not immediately, then job must be saved and the background service will handle it according to execution time :)
        if(!schedule){
            return reminderJobRepository.save(job);
        }

        // immediate execution
        return reminderUtil.sendAndFlush(job);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        try{
            reminderJobRepository.deleteReminderJobByJobId(UUID.fromString(id));
        }catch (NullPointerException ne){
            log.error("Invalid User Input");
        }catch (IllegalArgumentException e){
            log.error("invalid UUID");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<ReminderJob> saveAll(List<ReminderJob> jobs, boolean schedule) {
        if(jobs == null || jobs.size() == 0){
            throw new GenericClientException("invalid user Input",HttpStatus.BAD_REQUEST);
        }

        jobs.stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue()))
                .forEach(job -> {
                    if(!schedule && validation.isNumberValid(job.getMobileNumber())){
                        reminderUtil.sendAndFlush(job); // immediate execution
                    }else if(schedule && validation.isNumberValid(job.getMobileNumber()) && validation.isDateValid(job.getJobExecutionTime())){
                        reminderJobRepository.save(job); // scheduled jobs will be handled by event handler
                    }else{
                        job.setJobStatus(JobStatus.FAILED);
                    }});
        return jobs;
    }
}
