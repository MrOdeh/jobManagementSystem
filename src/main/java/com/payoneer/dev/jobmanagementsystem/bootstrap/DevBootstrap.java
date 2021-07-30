package com.payoneer.dev.jobmanagementsystem.bootstrap;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import com.payoneer.dev.jobmanagementsystem.repositories.JobTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DevBootstrap implements CommandLineRunner {

    private final JobTypeRepository jobTypeRepository;

    @Override
    public void run(String... args) throws Exception {

        jobTypeRepository.save(JobType.builder().jobName("test").build());

    }
}
