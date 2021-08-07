package com.payoneer.dev.jobmanagementsystem.utils;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/* here should a sms server or api for sending message
but here i mock the implementation */

@Log4j2
@RequiredArgsConstructor
@Component
public class ReminderUtil {

    private final ReminderJobRepository reminderJobRepository;

    @Transactional
    public ReminderJob sendAndFlush(ReminderJob job) {
        // every job starts with Queued status
        // update the status to running in order to maintain business need
        log.info("starting Reminder Job for# " + job);
        // the main perpouse of this block to avoid recode the job even handling and to avoid updating the status twice
        if(job.getJobStatus() == JobStatus.QUEUED) {
            job.setJobStatus(JobStatus.RUNNING);
            reminderJobRepository.saveAndFlush(job);
        }
        // this is a prototype and here im trying to simulate the Reminder Message
        try {
            if (LocalDateTime.now().getNano() % 2 != 0) {
                job.setJobStatus(JobStatus.SUCCESS);
                log.info("SUCCESS Reminder job for #" + job);
            } else {
                job.setJobStatus(JobStatus.FAILED);
                log.error("FAILED Reminder job for# " + job);
            }
        }catch (Exception ex){
            // this should be for API call to maintain system flexibility
            job.setJobStatus(JobStatus.FAILED);
            job.setNotes("Api Server Issue# " +  ex.getMessage());
        }finally {
            // update the date of completion
            job.setCompletedAt(LocalDateTime.now());
        }
        return reminderJobRepository.saveAndFlush(job);

    }
}
