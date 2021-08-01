package com.payoneer.dev.jobmanagementsystem.utils;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Component
public class ReminderUtil {

    private final ReminderJobRepository reminderJobRepository;

    @Transactional
    public ReminderJob sendAndFlush(ReminderJob job) {

        // every job starts with Queued status
        // update the status to running in order to martian business need
        log.info("starting Reminder Job for# " + job);
        job.setJobStatus(JobStatus.RUNNING);
        reminderJobRepository.saveAndFlush(job);

        // this is a prototype and here im trying to simulate :P
        if(LocalDateTime.now().getNano() % 2 != 0){
            job.setJobStatus(JobStatus.SUCCESS);
            log.info("SUCCESS Reminder job for #" + job);
        }else{
            job.setJobStatus(JobStatus.FAILED);
            log.warn("FAILED Reminder job for# " + job);
        }

        // update the date of completion
        job.setCompletedAt(LocalDateTime.now());
        return reminderJobRepository.saveAndFlush(job);

    }
}
