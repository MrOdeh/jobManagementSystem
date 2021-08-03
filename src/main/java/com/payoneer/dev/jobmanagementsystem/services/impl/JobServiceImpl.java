package com.payoneer.dev.jobmanagementsystem.services.impl;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.repositories.JobRepository;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<String> findAllJobNames() {
        return jobRepository.findAll()
                .stream()
                .map(job -> job.getJobType())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> findAllUnprocessedJobs() {
        return jobRepository.findAllByJobStatusAndJobExecutionTimeBetween(
                JobStatus.QUEUED, LocalDateTime.now(), LocalDateTime.now().plusSeconds(2));
    }

    @Override
    public List<Job> findAllProcessedJobs() {
        return jobRepository.findAllProccessdJobs(Arrays.asList(JobStatus.FAILED, JobStatus.SUCCESS, JobStatus.RUNNING));
    }

    @Override
    public List<Job> findAllReminderJobs() {
        return jobRepository.findAllByJobType("reminder");
    }

    @Override
    public List<Job> findAllEmailJobs() {
        return jobRepository.findAllByJobType("email");
    }

    @Override
    public List<Job> findAllQueuedJobs() {
        return jobRepository.findAllByJobStatus(JobStatus.QUEUED);
    }

    @Override
    public List<Job> saveAll(List<Job> jobs) {
        return jobRepository.saveAll(jobs);
    }

}
