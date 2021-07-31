package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReminderJobServiceImplTest {

    ReminderJob reminderJob;
    UUID uuid;
    @InjectMocks
    ReminderJobServiceImpl reminderJobService;
    @Mock
    ReminderJobRepository reminderJobRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        reminderJob = new ReminderJob();
        uuid = UUID.randomUUID();
    }

    @Test
    void findById() {
        //verify(jobTypeRepository, times(1)).findJobTypeByJobName(any(String.class));
        UUID uuid = UUID.randomUUID();
        when(reminderJobRepository.findOneByJobId(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(reminderJob));
        ReminderJob id = reminderJobService.findById(uuid.toString());
        verify(reminderJobRepository, times(1)).findOneByJobId(any(UUID.class));
    }
}