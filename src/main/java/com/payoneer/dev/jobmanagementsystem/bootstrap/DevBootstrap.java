package com.payoneer.dev.jobmanagementsystem.bootstrap;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Component
public class DevBootstrap implements CommandLineRunner {

    private final JobService jobService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("Loading Random Data...");
        ArrayList<Job> jobs = new ArrayList<>();
        jobService.saveAll(createJobs(jobs, 500));
        log.info("there are 1000 record has been loaded");
        log.info("Data has been loaded on http://localhost:8080/h2-console/");
        log.info("H2 credentials : ");
        log.info("Username: as");
        log.info("Password: password");
        log.info("swagger link : /swagger-ui.html");
    }

    private final List<Job> createJobs(List<Job> jobs, int count){

        Job job;
        for(int index = 0; index < count; index++){

            JobPriority jobPriority = index % 2 == 0 ? JobPriority.LOW : index % 3 == 0 ? JobPriority.MEDIUM : JobPriority.HIGH;

            job = ReminderJob.builder()
                    .jobExecutionTime(LocalDateTime.now().plusSeconds(index))
                    .jobPriority(jobPriority)
                    .reminderDescription("Reminder #" + index)
                    .mobileNumber("007983130" + index)
                    .build();
            jobs.add(job);

            job = EmailJob.builder()
                    .jobExecutionTime(LocalDateTime.now().plusSeconds(index))
                    .jobPriority(jobPriority)
                    .sender(String.format("Mail_%s@payoneer.com", index))
                    .receiver(String.format("Mail_%s@payoneer.com", index + (index + 1)))
                    .messageBody("Helllo :)")
                    .build();

            jobs.add(job);
        }
        return jobs;
    }

}
