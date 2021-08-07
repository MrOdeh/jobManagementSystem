package com.payoneer.dev.jobmanagementsystem.event.publishers;

import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.event.model.EmailJobEvent;
import com.payoneer.dev.jobmanagementsystem.event.model.ReminderJobEvent;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;

@Log4j2
@AllArgsConstructor
@Component
public class ReminderJobEventPublisher {

    private final ReminderJobRepository reminderJobRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 2000)
    void publishEvent(){
        log.info("**********" + Thread.currentThread().getName());
        reminderJobRepository
                .findAllByJobStatusAndExecutionTimeBetween(JobStatus.QUEUED, LocalDateTime.now(), LocalDateTime.now().plusSeconds(2))
                .stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue())) // sort by JobPriority
                .map(job -> new ReminderJobEvent(job))
                .forEach(event -> applicationEventPublisher.publishEvent(event));
    }
}
