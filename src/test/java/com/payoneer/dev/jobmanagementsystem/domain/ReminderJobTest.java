package com.payoneer.dev.jobmanagementsystem.domain;

import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReminderJobTest {

    LocalDateTime executionTime;
    ReminderJob reminderJob;

    @BeforeEach
    void setUp() {
        executionTime = LocalDateTime.now().plusHours(2);
        reminderJob = ReminderJob.builder()
                .reminderDescription("nothing")
                .jobPriority(JobPriority.LOW)
                .jobExecutionTime(executionTime)
                .mobileNumber("nothing")
                .build();
    }

    @Test
    void getReminderDescription() {
        assertTrue(reminderJob.getReminderDescription().equals("nothing"));
    }

    @Test
    void getMobileNumber() {
        assertTrue(reminderJob.getMobileNumber().equals("nothing"));
    }

}