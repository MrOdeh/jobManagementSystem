package com.payoneer.dev.jobmanagementsystem.bootstrap;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import com.payoneer.dev.jobmanagementsystem.repositories.JobRepository;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class DevBootstrap implements CommandLineRunner {

    private final EmailJobRepository emailJobRepository;
    private final ReminderJobRepository reminderJobRepository;
    private final JobRepository jobRepository;
    private final EmailJobService emailJobService;
    private final JobService jobService;

    @Override
    public void run(String... args) throws Exception {

        log.info("loading data completed");
        createJobs();
    }


    private final void createJobs(){

        ReminderJob reminderJob = new ReminderJob(LocalDateTime.now(), JobPriority.LOW, "description", "00798313098");
        ReminderJob anotherRminder = new ReminderJob(LocalDateTime.now(), JobPriority.MEDIUM, "description2", "0079");

        EmailJob emailJob = new EmailJob(LocalDateTime.now(), JobPriority.MEDIUM, "body", "sender","revicer");
        EmailJob anotherEmail = new EmailJob(LocalDateTime.now(), JobPriority.HIGH, "body2", "sender2","revicer2");

        emailJobRepository.save(emailJob);
        reminderJobRepository.save(reminderJob);
        EmailJob saveEmail = emailJobRepository.save(anotherEmail);
        ReminderJob savedReminder = reminderJobRepository.save(anotherRminder);

        log.info("finidin!!!!");

        Job emailJobJob = emailJobRepository.findOneByJobId(UUID.fromString(saveEmail.getJobId().toString())).get();
        Job reminderJobJob = reminderJobRepository.findOneByJobId(UUID.fromString(savedReminder.getJobId().toString())).get();
        System.out.println( "email# " + emailJobJob);

        System.out.println( "reminder# " + reminderJobJob);

        EmailJob build = EmailJob.builder().messageBody("sdd").sender("Ss").receiver("Sss").jobPriority(JobPriority.LOW).jobExecutionTime(LocalDateTime.now()).build();
        emailJobRepository.save(build);

        emailJobService.deleteById(null);

        System.out.println("getting the all available jobs...");
        System.out.println(jobService.findAll());
    }

}
