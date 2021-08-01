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
import com.payoneer.dev.jobmanagementsystem.services.ReminderJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;



@Log4j2
@RequiredArgsConstructor
@Component
public class DevBootstrap implements CommandLineRunner {

    private final EmailJobRepository emailJobRepository;
    private final ReminderJobRepository reminderJobRepository;
    private final JobRepository jobRepository;
    private final EmailJobService emailJobService;
    private final ReminderJobService reminderJobService;
    private final JobService jobService;



    @Override
    public void run(String... args) throws Exception {

        log.info("loading data completed");
        createJobs();
        log.info("sending messages :)");

    }



    private final void createJobs(){

        ReminderJob reminderJob = new ReminderJob(LocalDateTime.now(), JobPriority.LOW, "description", "98313098");
        ReminderJob anotherRminder = new ReminderJob(LocalDateTime.now(), JobPriority.MEDIUM, "description2", "79387482");

        EmailJob emailJob = new EmailJob(LocalDateTime.now(), JobPriority.MEDIUM, "body", "ma@payoneer.com","mad@payoneer.com");
        EmailJob anotherEmail = new EmailJob(LocalDateTime.now(), JobPriority.HIGH, "body2", "mas@payoneer.com","ma@payoneer.com");

        emailJobService.save(emailJob, false);
        reminderJobService.save(reminderJob, false);
        EmailJob saveEmail = emailJobService.save(anotherEmail, false);
        ReminderJob savedReminder = reminderJobService.save(anotherRminder, false);

        log.info("finidin!!!!");

        Job emailJobJob = emailJobService.findById(saveEmail.getJobId().toString());
        Job reminderJobJob = reminderJobService.findById(savedReminder.getJobId().toString());
        System.out.println( "email# " + emailJobJob);

        System.out.println( "reminder# " + reminderJobJob);

        EmailJob build = EmailJob.builder().messageBody("sdd").sender("Ss").receiver("Sss").jobPriority(JobPriority.LOW).jobExecutionTime(LocalDateTime.now()).build();
        emailJobRepository.save(build);

        emailJobService.deleteById(null);

        System.out.println("getting the all available jobs...");
        System.out.println(jobService.findAll());
    }

}
