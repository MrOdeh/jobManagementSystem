package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;


public interface JobTypeService{

    JobType findById(String id);

    JobType findByJobName(String name);

    JobType save(JobType job);

    JobType update(JobType job);

    void deleteById(String id);



}
