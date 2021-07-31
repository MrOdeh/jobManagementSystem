package com.payoneer.dev.jobmanagementsystem.domain;

import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EmailJobTest {

    EmailJob job;
    LocalDateTime executionTime;

    @BeforeEach
    void setUp() {
        executionTime = LocalDateTime.now();
        job = EmailJob.builder()
                .jobExecutionTime(executionTime)
                .messageBody("nothing")
                .sender("me")
                .receiver("you")
                .jobPriority(JobPriority.MEDIUM)
                .build();
    }

    @Test
    void getMessageBody() {

        assertNotNull(job);
        assertTrue(job.getMessageBody().equals("nothing"));
    }

    @Test
    void getSender() {
        assertNotNull(job);
        assertTrue(job.getSender().equals("me"));
    }

    @Test
    void getReceiver() {
        assertNotNull(job);
        assertTrue(job.getReceiver().equals("you"));
    }

    @Test
    void getExecutionTime() {
        assertNotNull(job);
        assertEquals(executionTime, job.getJobExecutionTime());
        assertTrue(executionTime.toString().equals(job.getJobExecutionTime().toString()));
    }

    @Test
    void getExecutionTimeNowBefore() {
        assertNotEquals(job.getJobExecutionTime(), null);
        assertFalse(job.getJobExecutionTime().equals(LocalDateTime.now().minusMinutes(1)));
    }

    @Test
    void getPriority() {
        assertNotNull(job);
        assertEquals(job.getJobPriority(), JobPriority.MEDIUM);
        assertNotEquals(job.getJobPriority(), JobPriority.LOW);
    }


}