package com.payoneer.dev.jobmanagementsystem.domain;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "email_job")
@DiscriminatorValue(value = "email")
public class EmailJob extends Job {

    @Column(name = "message_boyd")
    private String messageBody;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Builder // CUSTOMIZED BUILDER
    public EmailJob(LocalDateTime jobExecutionTime, JobPriority jobPriority, String messageBody, String sender, String receiver) {
        super(jobExecutionTime, jobPriority);
        this.messageBody = messageBody;
        this.sender = sender;
        this.receiver = receiver;
    }
}
