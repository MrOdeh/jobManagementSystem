package com.payoneer.dev.jobmanagementsystem.domain;

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

    @Column(name = "subject")
    private String messageSubject;

    @Column(name = "sender")
    private String sender;

    @Column(name = "recipients")
    private String recipients;

    @Column(name = "is_html")
    private Boolean isHtml;

    @Column(name = "attachment_path")
    private String attachmentPath;

    @Column(name = "cc")
    private String ccList;

    @Column(name = "bcc")
    private String bccList;

    @Builder // CUSTOMIZED BUILDER
    public EmailJob(LocalDateTime jobExecutionTime, JobPriority jobPriority, String messageBody, String sender, String recipients,
                    String messageSubject, Boolean isHtml, String attachmentPath, String ccList, String bccList) {
        super(jobExecutionTime, jobPriority);
        this.messageBody = messageBody;
        this.sender = sender;
        this.recipients = recipients;
        this.messageSubject = messageSubject;
        this.isHtml = isHtml;
        this.attachmentPath = attachmentPath;
        this.ccList = ccList;
        this.bccList = bccList;
    }
}
