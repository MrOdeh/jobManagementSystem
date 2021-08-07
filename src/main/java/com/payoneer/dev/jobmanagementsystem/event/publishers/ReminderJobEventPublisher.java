package com.payoneer.dev.jobmanagementsystem.event.publishers;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.event.model.EmailJobEvent;
import com.payoneer.dev.jobmanagementsystem.event.model.ReminderJobEvent;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Component
public class ReminderJobEventPublisher {

    private final ReminderJobRepository reminderJobRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

/*    fetch data which has :
    // 1-Queued status
    // 2-execution dateTime within now() and now() + 2 seconds
    // then --
       3-update their status accordingly to RUNNING
       4-save them all in on transaction
    // 5- sort them based on Job Propriety --> this can be straightforward from the query itself but I prefer on-fly implementation
    // 4- publish them on parallel approach*/

    @Transactional
    @Scheduled(fixedRate = 2000)
    void publishEvent(){
        List<ReminderJob> jobs =
                reminderJobRepository.findAllByJobStatusAndExecutionTimeBetween
                        (JobStatus.QUEUED, LocalDateTime.now(), LocalDateTime.now().plusSeconds(2));
        jobs.stream().forEach(job -> job.setJobStatus(JobStatus.RUNNING));
        List<ReminderJob> returnedJobs = reminderJobRepository.saveAll(jobs);

        returnedJobs
                .stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue())) // sort by JobPriority
                .map(job -> new ReminderJobEvent(job))
                .forEach(event -> applicationEventPublisher.publishEvent(event));
    }
}
