package com.payoneer.dev.jobmanagementsystem.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobTypeTest {

    JobType job;

    @BeforeEach
    public void setUp() {
        job = new JobType();
    }

    @Test
    public void getName(){
        String jobName = "Reminder";
        job.setJobName(jobName);
        assertEquals(jobName, job.getJobName());
    }
}