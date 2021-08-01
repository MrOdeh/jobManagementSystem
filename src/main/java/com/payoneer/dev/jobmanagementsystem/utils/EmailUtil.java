package com.payoneer.dev.jobmanagementsystem.utils;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Component
public class EmailUtil {

    private final EmailJobRepository emailJobRepository;

    public EmailJob sendAndFlush(EmailJob job) {
        // every job starts with Queued status
        // update the status to running in order to martian business need
        log.info("starting Email Job for# " + job);
        job.setJobStatus(JobStatus.RUNNING);
        emailJobRepository.saveAndFlush(job);

        // this is a prototype and here im trying to simulate :P
        if(LocalDateTime.now().getNano() % 2 == 0){
            job.setJobStatus(JobStatus.SUCCESS);
            log.error("SUCCESS Reminder job for# " + job);
        }else{
            job.setJobStatus(JobStatus.FAILED);
            log.error("FAILED Reminder job for# " + job);
        }

        // update the date of completion
        job.setCompletedAt(LocalDateTime.now());
         return emailJobRepository.saveAndFlush(job);
    }
}
