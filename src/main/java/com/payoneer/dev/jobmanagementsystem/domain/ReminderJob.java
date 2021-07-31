package com.payoneer.dev.jobmanagementsystem.domain;

import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reminder_job")
@DiscriminatorValue(value = "reminder")
public class ReminderJob extends Job {

    @Column(name = "reminder_description")
    private String reminderDescription;

    @Column(name = "mobile")
    private String mobileNumber;

    @Builder
    public ReminderJob(LocalDateTime jobExecutionTime, JobPriority jobPriority, String reminderDescription, String mobileNumber) {
        super(jobExecutionTime, jobPriority);
        this.reminderDescription = reminderDescription;
        this.mobileNumber = mobileNumber;
    }
}
