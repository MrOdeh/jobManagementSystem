package com.payoneer.dev.jobmanagementsystem.event.listeners;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.event.model.EmailJobEvent;
import com.payoneer.dev.jobmanagementsystem.utils.EmailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class EmailJobEventListener {

    private final EmailUtil emailUtil;
    private static final String EMAIL_EVENT_MESSAGE = "Thread# %s took Email %s";

    @Async("EmailtaskExecutor")
    @EventListener
    void handleEmailJobEvent(EmailJobEvent job){
        log.info(String.format(EMAIL_EVENT_MESSAGE, Thread.currentThread().getName(), job));
        emailUtil.sendAndFlush((EmailJob) job.getEvent());
    }
}
