package com.payoneer.dev.jobmanagementsystem.event.listeners;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.event.model.ReminderJobEvent;
import com.payoneer.dev.jobmanagementsystem.utils.ReminderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class ReminderJobEventListener {

    private final ReminderUtil reminderUtil;
    private static final String REMINDER_EVENT_MESSAGE = "Thread# %s took Reminder %s";

    @Async("RemindertaskExecutor")
    @EventListener
    void handleReminderJobEvent(ReminderJobEvent job){
        log.info(String.format(REMINDER_EVENT_MESSAGE, Thread.currentThread().getName(), job));
        reminderUtil.sendAndFlush((ReminderJob) job.getEvent());
    }
}
