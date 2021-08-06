package com.payoneer.dev.jobmanagementsystem.services.impl;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import com.payoneer.dev.jobmanagementsystem.utils.EmailUtil;
import com.payoneer.dev.jobmanagementsystem.utils.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/*
@ExtendWith(SpringExtension.class)*/
class EmailJobServiceImplTest {

    EmailJob job;
    UUID uuid;

    @Mock
    EmailJobRepository emailJobRepository;

    @Mock
    EmailUtil emailUtil;

    @Mock
    Validation validation;

    @InjectMocks
    EmailJobServiceImpl emailJobService;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        job = new EmailJob();
        uuid = UUID.randomUUID();
    }

    @Test
    void findById() {

        UUID uuid = UUID.randomUUID();
        when(emailJobRepository.findOneByJobId(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(job));
        EmailJob id = emailJobService.findById(uuid.toString());
        verify(emailJobRepository, times(1)).findOneByJobId(any(UUID.class));
    }

    @Test
    void findAll() {

        List<EmailJob> jobs = new ArrayList<>();
        jobs.add(job);
        when(emailJobRepository.findAll()).thenReturn(jobs);
        List<EmailJob> anotherJobs = emailJobService.findAll();
        verify(emailJobRepository, times(1)).findAll();
        assertTrue(1 == jobs.size());

    }

    @Test
    void save_GenericClientException() {

        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        JobPriority jobPriority = JobPriority.MEDIUM;
        String sender = "skjdksjd@payoneer.com";
        String reciver = "aksjkasj@payoneer.com";
        String body = "stop pretending happy ending :P ";
        job = EmailJob.builder()
                .jobExecutionTime(now)
                .jobPriority(jobPriority)
                .sender(sender)
                .recipients(reciver)
                .messageBody(body)
                .build();

        when(emailJobRepository.save(any())).thenReturn(job);
        Assertions.assertThrows(GenericClientException.class, () -> {
            emailJobService.save(job,false);
        });
    }

    @Test
    void save_Scheduled() {

        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        JobPriority jobPriority = JobPriority.MEDIUM;
        String sender = "Mail@payoneer.com";
        String reciver = "Mail@payoneer.com";
        String body = "nice to meet you";
        job = EmailJob.builder()
                .jobExecutionTime(now)
                .jobPriority(jobPriority)
                .sender(sender)
                .recipients(reciver)
                .messageBody(body)
                .build();

        when(validation.isEmailValid(anyString())).thenReturn(Boolean.TRUE);
        when(validation.isDateValid(any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        when(emailJobRepository.save(any())).thenReturn(job);
        EmailJob savedEmail = emailJobService.save(job, true);
        verify(emailJobRepository,times(1)).save(job);
        assertTrue(savedEmail.getJobPriority() == jobPriority);
        assertTrue(sender.equals(((EmailJob)savedEmail).getSender()));
        assertTrue(reciver.equals(((EmailJob)savedEmail).getRecipients()));
        assertTrue(body.equals(((EmailJob)savedEmail).getMessageBody()));

    }

    @Test
    void save_NotScheduled() {

        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        JobPriority jobPriority = JobPriority.MEDIUM;
        String sender = "Mail@payoneer.com";
        String reciver = "Mail@payoneer.com";
        String body = "nice to meet you";
        job = EmailJob.builder()
                .jobExecutionTime(now)
                .jobPriority(jobPriority)
                .sender(sender)
                .recipients(reciver)
                .messageBody(body)
                .build();

        when(validation.isEmailValid(anyString())).thenReturn(Boolean.TRUE);
        when(validation.isDateValid(any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        when(emailUtil.sendAndFlush(any(EmailJob.class))).thenReturn(job);
        EmailJob savedEmail = emailJobService.save(job, true);
        verify(emailJobRepository,times(1)).save(job);
        assertTrue(job.getJobPriority() == jobPriority);
        assertTrue(sender.equals(((EmailJob)job).getSender()));
        assertTrue(reciver.equals(((EmailJob)job).getRecipients()));
        assertTrue(body.equals(((EmailJob)job).getMessageBody()));

    }

    @Test
    void save_isEmailNotVaild() {

        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        JobPriority jobPriority = JobPriority.MEDIUM;
        String sender = "Mail@anotherThing.com";
        String reciver = "Mail@anotherThing.com";
        String body = "stop pretending happy ending :P ";
        job = EmailJob.builder()
                .jobExecutionTime(now)
                .jobPriority(jobPriority)
                .sender(sender)
                .recipients(reciver)
                .messageBody(body)
                .build();

        when(emailJobRepository.save(any())).thenReturn(job);
        when(validation.isEmailValid(anyString())).thenReturn(Boolean.FALSE);
        Assertions.assertThrows(GenericClientException.class, () -> {
            emailJobService.save(job, false);
        });
    }

}