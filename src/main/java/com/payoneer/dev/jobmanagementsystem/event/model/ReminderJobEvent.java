package com.payoneer.dev.jobmanagementsystem.event.model;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class ReminderJobEvent extends JobEvent<ReminderJob> implements Serializable {

    public static final long serialVersionUID = 33L;
    public ReminderJobEvent(ReminderJob reminderJob) {
        super(reminderJob, reminderJob.getJobType());
    }
}
