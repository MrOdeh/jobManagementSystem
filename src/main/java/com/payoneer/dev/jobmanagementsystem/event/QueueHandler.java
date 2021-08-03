package com.payoneer.dev.jobmanagementsystem.event;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import com.payoneer.dev.jobmanagementsystem.utils.EmailUtil;
import com.payoneer.dev.jobmanagementsystem.utils.ReminderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class QueueHandler {

    private final EmailUtil emailUtil;
    private final ReminderUtil reminderUtil;
    private static final String THREAD_MESSAGE = "Thread# %s took Job %s";

    @Async // it will use available thread in pool config
    public void jobHandler(Job job){ // in case N of possible jobs increased i would like to replace switch case with design pattern approach
        log.info(String.format(THREAD_MESSAGE, Thread.currentThread().getName(), job));
        switch (job.getJobType()){
            case "email":
                emailUtil.sendAndFlush((EmailJob) job);
                break;
            case "reminder":
                reminderUtil.sendAndFlush((ReminderJob) job);
                break;
            default:
                 /*inform any related parties
                 here we can skip the new type of jobs since it can be handled by another service completely isolated
                 and there will be NO CHANGE on current implementation */
                log.info("its related to another job type");
        }
    }
}
