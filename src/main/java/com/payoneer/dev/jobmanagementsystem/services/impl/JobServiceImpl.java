package com.payoneer.dev.jobmanagementsystem.services.impl;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.repositories.JobRepository;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<String> findAllJobsNames() {
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
    public List<Job> findAllJobsByStatusAndExecutionTime(JobStatus status, LocalDateTime from, LocalDateTime to) {
        return jobRepository.findAllByJobStatusAndExecutionTimeBetween(status,from, to);
    }

    @Override
    public List<Job> findAllJobsByType(String type) {
        return jobRepository.findAllByJobType(type);
    }

    @Override
    public List<Job> findAllJobsByStatus(JobStatus status) {
        return jobRepository.findAllByJobStatus(status);
    }

    @Override
    public List<Job> saveAll(List<Job> jobs) {
        return jobRepository.saveAll(jobs);
    }

    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }

}
