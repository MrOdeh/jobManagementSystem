package com.payoneer.dev.jobmanagementsystem.event.model;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class EmailJobEvent extends JobEvent<EmailJob> implements Serializable {

    public static final long serialVersionUID = 44L;
    public EmailJobEvent(EmailJob emailJob) {
        super(emailJob, emailJob.getJobType());
    }
}
