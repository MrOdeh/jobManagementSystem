package com.payoneer.dev.jobmanagementsystem.services.impl;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import com.payoneer.dev.jobmanagementsystem.services.impl.ReminderJobServiceImpl;
import com.payoneer.dev.jobmanagementsystem.utils.ReminderUtil;
import com.payoneer.dev.jobmanagementsystem.utils.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReminderJobServiceImplTest {

    ReminderJob reminderJob;

    LocalDateTime now;

    UUID uuid;

    @InjectMocks
    ReminderJobServiceImpl reminderJobService;

    @Mock
    ReminderJobRepository reminderJobRepository;

    @Mock
    Validation validation;

    @Mock
    ReminderUtil reminderUtil;


    @BeforeEach
    public void setup(){
        now = LocalDateTime.now();
        MockitoAnnotations.initMocks(this);
        reminderJob = ReminderJob.builder()
                .jobExecutionTime(now)
                .jobPriority(JobPriority.MEDIUM)
                .mobileNumber("928949795")
                .reminderDescription("nothing")
                .build();
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

    @Test
    void findAll() {
        List<ReminderJob> reminders = new ArrayList<>();
        reminders.add(reminderJob);

        when(reminderJobRepository.findAll()).thenReturn(reminders);
        List<ReminderJob> all = reminderJobService.findAll();
        verify(reminderJobRepository, times(1)).findAll();
        assertTrue(all.get(0).getReminderDescription().equals(all.get(0).getReminderDescription()));
    }

    @Test
    void save_Scheduled(){

        when(reminderJobRepository.save(any(ReminderJob.class))).thenReturn(reminderJob);
        when(validation.isEmailValid(anyString())).thenReturn(Boolean.TRUE);
        when(validation.isNumberValid(anyString())).thenReturn(Boolean.TRUE);
        when(validation.isDateValid(any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        reminderJobService.save(reminderJob, true);
        verify(reminderJobRepository, times(1)).save(any(ReminderJob.class));
        assertTrue(reminderJob != null);
    }

    @Test
    void save_NotScheduled(){

        when(validation.isEmailValid(anyString())).thenReturn(Boolean.TRUE);
        when(validation.isNumberValid(anyString())).thenReturn(Boolean.TRUE);
        when(validation.isDateValid(any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        when(reminderUtil.sendAndFlush(any(ReminderJob.class))).thenReturn(reminderJob);
        reminderJobService.save(reminderJob, false);
        verify(reminderUtil, times(1)).sendAndFlush(any(ReminderJob.class));
        assertTrue(reminderJob != null);
    }

    @Test
    void save_InvalidMobileNumber(){

        when(reminderJobRepository.save(any(ReminderJob.class))).thenReturn(reminderJob);
        when(validation.isNumberValid(anyString())).thenReturn(Boolean.FALSE);
        Assertions.assertThrows(GenericClientException.class, () -> {
            reminderJobService.save(reminderJob, false);
        });
    }

    @Test
    void save_scheduleJob(){

        when(reminderJobRepository.save(any(ReminderJob.class))).thenReturn(reminderJob);
        when(validation.isNumberValid(anyString())).thenReturn(Boolean.TRUE);
        when(validation.isDateValid(any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        ReminderJob savedReminderJob = reminderJobService.save(reminderJob, true);

    }
}