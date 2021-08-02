package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobService {

    List<String> findAllJobNames();
    List<Job> findAll();
    List<Job> findAllUnprocessedJobs();
    List<Job> findAllProcessedJobs();
    List<Job> findAllReminderJobs();
    List<Job> findAllEmailJobs();
    List<Job> findAllQueuedJobs();
}
