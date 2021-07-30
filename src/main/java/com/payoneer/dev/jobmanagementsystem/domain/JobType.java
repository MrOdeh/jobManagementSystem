package com.payoneer.dev.jobmanagementsystem.domain;

import lombok.*;
import javax.persistence.*;


@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "job_type")
public class JobType extends BaseEntity {

    @Column(name = "jobName")
    private String jobName;

    public JobType(String jobName) {
        this.jobName = jobName;
    }

}
