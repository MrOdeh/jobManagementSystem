package com.payoneer.dev.jobmanagementsystem.services.impl;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import com.payoneer.dev.jobmanagementsystem.utils.EmailUtil;
import com.payoneer.dev.jobmanagementsystem.utils.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailJobServiceImpl implements EmailJobService {

    private final EmailJobRepository emailJobRepository;
    private final Validation validation;
    private final EmailUtil emailUtil;

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
    public EmailJob save(EmailJob job, boolean schedule) {
        // validate E-mail & execution time
        if(!(validation.isEmailValid(job.getSender()) && validation.isEmailValid(job.getReceiver()))){
            throw new GenericClientException("invalid email address",HttpStatus.BAD_REQUEST);
        }

        // if Not immediately then i have to validate the date
        if (schedule && !validation.isDateValid(job.getJobExecutionTime())) {
            throw new GenericClientException("Invalid Date",HttpStatus.BAD_REQUEST);
        }

        // Not immediately, then job must be saved and the background service will handle it according to execution time :)
        if(!schedule){
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
}