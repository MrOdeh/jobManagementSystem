package com.payoneer.dev.jobmanagementsystem.event.publishers;

import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.event.model.EmailJobEvent;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;

@Log4j2
@Component
@AllArgsConstructor
public class EmailJobEventPublisher {

    private final EmailJobRepository emailJobRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 2000)
    public void publishEvent(){
        log.info("**********" + Thread.currentThread().getName());
        emailJobRepository
                .findAllByJobStatusAndExecutionTimeBetween(JobStatus.QUEUED, LocalDateTime.now(), LocalDateTime.now().plusSeconds(2))
                .stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue())) // sort by JobPriority
                .map(job -> new EmailJobEvent(job))
                .forEach(event -> applicationEventPublisher.publishEvent(event));
    }
}
