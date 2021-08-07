package com.payoneer.dev.jobmanagementsystem.event.model;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EmailJobEvent extends JobEvent<EmailJob> {
    
    public EmailJobEvent(EmailJob emailJob) {
        super(emailJob, emailJob.getJobType());
    }
}
