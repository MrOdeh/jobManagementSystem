package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import com.payoneer.dev.jobmanagementsystem.repositories.JobTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class JobTypeServiceImplTest {

    @InjectMocks
    JobTypeServiceImpl jobTypeService;

    @Mock
    JobTypeRepository jobTypeRepository;

    JobType job;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        job = new JobType();
    }
    @Test
    void findByJobName() {

        job.setJobName("Reminder");
        when(jobTypeRepository.findJobTypeByJobName(anyString())).thenReturn(java.util.Optional.ofNullable(job));
    }

    @Test
    public void findByJobName_RunTimeException(){
        assertThrows(RuntimeException.class,
                () -> {
            jobTypeService.findByJobName(null);
                });
    }

    @Test
    void save() {
        job.setJobName("Email");
        when(jobTypeRepository.save(job)).thenReturn(job);
        JobType byEmail = jobTypeService.findByJobName("Email");
        assertNotNull(byEmail);

        verify(jobTypeRepository, times(1)).findJobTypeByJobName(any(String.class));
    }
}