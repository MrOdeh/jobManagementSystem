package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.Job;

import java.util.List;

public interface JobService {

    List<String> findAllJobNames();
    List<Job> findAll();
    List<Job> findAllUnprocessedJobs();
}
