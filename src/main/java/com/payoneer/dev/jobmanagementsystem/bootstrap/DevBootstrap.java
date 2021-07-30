package com.payoneer.dev.jobmanagementsystem.bootstrap;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import com.payoneer.dev.jobmanagementsystem.repositories.JobTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class DevBootstrap implements CommandLineRunner {

    private final JobTypeRepository jobTypeRepository;

    @Override
    public void run(String... args) throws Exception {

        log.info("loading data completed");
        createJobTypeSamples();
    }

    private void createJobTypeSamples(){
        JobType email = JobType.builder().jobName("Email").build();
        JobType reminder = JobType.builder().jobName("Reminder").build();
        JobType fileHandler = JobType.builder().jobName("FileHanlder").build();

        jobTypeRepository.save(email);
        jobTypeRepository.save(reminder);
        jobTypeRepository.save(fileHandler);
    }
}
