package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import com.payoneer.dev.jobmanagementsystem.services.JobTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JobTypeControllerTest {
    JobType job;

    @Mock
    JobTypeServiceImpl jobTypeService;

    @InjectMocks
    JobTypeController jobTypeController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        job = new JobType();
        job.setJobName("Reminder");
    }
    @Test
    void getByName() {
        when(jobTypeController.getByName(anyString())).thenReturn(null);
        JobType byName = jobTypeController.getByName("Reminder");
        assertNull(byName);
        verify(jobTypeService, atMostOnce()).findByJobName(anyString());
    }

    @Test
    void create() {
        when(jobTypeController.create(any(JobType.class))).thenReturn(job);

        when(jobTypeService.findByJobName(anyString())).thenReturn(job);
        JobType anotherJob = jobTypeService.findByJobName(job.getJobName());

        assertNotNull(anotherJob);
        assertEquals(anotherJob.getJobName(), job.getJobName());
    }

    @Test
    public void update(){

        assertNotNull(job);
    }
}