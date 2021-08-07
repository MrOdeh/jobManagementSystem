package com.payoneer.dev.jobmanagementsystem.event.model;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReminderJobEvent extends JobEvent<ReminderJob>{

    public ReminderJobEvent(ReminderJob reminderJob) {
        super(reminderJob, reminderJob.getJobType());
    }
}
