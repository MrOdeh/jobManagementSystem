package com.payoneer.dev.jobmanagementsystem.utils;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Component
public class EmailUtil {

    private final EmailJobRepository emailJobRepository;
    // JUST IN CASE IF WE NEED TO APPLY IT FUTURE I WILL LEAVE HERE.
    private final JavaMailSender javaMailSender;

    // FAKE
    @Transactional
/*    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)*/
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

    @Deprecated // this is the right way but here i will not use it
    public EmailJob sendEmail(EmailJob job){
        // every job starts with Queued status
        // update the status to running in order to martian business need
        log.info("starting Email Job for# " + job);
        job.setJobStatus(JobStatus.RUNNING);
        emailJobRepository.saveAndFlush(job);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(job.getReceiver());

        msg.setSubject("Pyonnier");
        msg.setText(job.getMessageBody());

        javaMailSender.send(msg);
        job.setJobStatus(JobStatus.SUCCESS);
        return emailJobRepository.saveAndFlush(job);
    }


}
