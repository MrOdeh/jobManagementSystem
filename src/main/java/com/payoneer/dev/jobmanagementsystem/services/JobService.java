package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface JobService {

    List<String> findAllJobsNames();

    List<Job> findAll();

    List<Job> findAllJobsByStatusAndExecutionTime(JobStatus status, LocalDateTime from, LocalDateTime to);

    List<Job> findAllJobsByType(String type);

    List<Job> findAllJobsByStatus(JobStatus status);

    List<Job> saveAll(List<Job> jobs);

    Job save(Job job);
}
