package com.payoneer.dev.jobmanagementsystem.bootstrap;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import com.payoneer.dev.jobmanagementsystem.repositories.JobRepository;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import com.payoneer.dev.jobmanagementsystem.services.ReminderJobService;
import com.payoneer.dev.jobmanagementsystem.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


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
    private final EmailUtil emailUtil;


    @Override
    public void run(String... args) throws Exception {

        log.info("loading data completed");
        //createJobs();
        log.info("sending messages :)");
        /*jobRepository.findAll()
                .forEach(p -> System.out.println("type # " + p.getJobType() + "  & executionTime# " + p.getJobExecutionTime() + "  status# " + p.getJobStatus()));
*/
        createRandomly();
//        System.out.println("neeededdd");
//        jobRepository.findAllByJobStatusAndJobExecutionTimeBetween(JobStatus.QUEUED, LocalDateTime.now(), LocalDateTime.now().plusSeconds(2))
//                .forEach(p -> System.out.println("type # " + p.getJobType() + "  & executionTime# " + p.getJobExecutionTime() + "  status# " + p.getJobStatus()));

        sortWay();

    }

    private void sortWay() {
        Job job;
        List<Job> jobs = new ArrayList<>();
        for(int index = 0; index < 20; index++) {
            job = new ReminderJob(LocalDateTime.now().plusSeconds(index),
                    index % 2 == 0 ? JobPriority.LOW : index == 3 || index == 6 || index == 12 || index == 16 ? JobPriority.MEDIUM : JobPriority.HIGH
                    , "description", "98313098");
            jobs.add(job);
            job = new EmailJob(LocalDateTime.now().plusSeconds(index),
                    index % 2 == 0 ? JobPriority.HIGH : index == 3 || index == 6 || index == 12 || index == 16 ? JobPriority.MEDIUM : JobPriority.LOW ,
                    "body", "ma@payoneer.com", "mad@payoneer.com");
            jobs.add(job);
        }

        System.out.println("finished### viewing");

        jobs.stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue()))
                .forEach(p -> System.out.println("type# " + p.getJobType() + "  job priority# " + p.getJobPriority()));
    }

    public void createRandomly() {

        for(int index = 0; index < 200; index++) {
            EmailJob emailJob = new EmailJob(LocalDateTime.now().plusSeconds(index),
                    index % 2 == 0 ? JobPriority.HIGH : index == 3 || index == 6 || index == 12 || index == 16 ? JobPriority.MEDIUM : JobPriority.LOW ,
                    "body", "ma@payoneer.com", "mad@payoneer.com");
            //emailJobRepository.save(emailJob);
            ReminderJob reminderJob = new ReminderJob(LocalDateTime.now().plusSeconds(index),
                    index % 2 == 0 ? JobPriority.LOW : index == 3 || index == 6 || index == 12 || index == 16 ? JobPriority.MEDIUM : JobPriority.HIGH
                    , "description", "98313098");
            //reminderJobRepository.save(reminderJob);
            emailUtil.sendAndFlush(emailJob);
        }
    }
    //@Transactional
    public void createJobs(){

        ReminderJob reminderJob = new ReminderJob(LocalDateTime.now(), JobPriority.LOW, "description", "98313098");
        ReminderJob anotherRminder = new ReminderJob(LocalDateTime.now(), JobPriority.MEDIUM, "description2", "79387482");

        EmailJob emailJob = new EmailJob(LocalDateTime.now().plusSeconds(5), JobPriority.MEDIUM, "body", "ma@payoneer.com","mad@payoneer.com");
        EmailJob anotherEmail = new EmailJob(LocalDateTime.now(), JobPriority.HIGH, "body2", "mas@payoneer.com","ma@payoneer.com");

        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(5));
        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(7));
        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(6));
        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(22));
        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(464));
        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(556));
        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(3434));
        emailJobService.save(emailJob, false);
        emailJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(15));
        emailJobService.save(emailJob, false);

        reminderJobService.save(reminderJob, false);

        reminderJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(4));
        reminderJobService.save(reminderJob, false);
        reminderJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(3));
        reminderJobService.save(reminderJob, false);
        reminderJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(423));
        reminderJobService.save(reminderJob, false);
        reminderJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(42323));
        reminderJobService.save(reminderJob, false);
        reminderJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(4232));
        reminderJobService.save(reminderJob, false);
        reminderJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(4232));
        reminderJobService.save(reminderJob, false);
        reminderJob.setJobExecutionTime(LocalDateTime.now().plusSeconds(4232));
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
