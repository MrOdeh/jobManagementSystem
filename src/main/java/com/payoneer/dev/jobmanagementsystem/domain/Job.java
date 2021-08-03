package com.payoneer.dev.jobmanagementsystem.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

// this is the core of this project
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "job")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "job_type")
public class Job implements Serializable {

    public static final long serialVersionUID = 12L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "job_id", length = 36)
    private UUID jobId;

    @CreationTimestamp // for auditing
    @Setter(AccessLevel.NONE)
    @Column(name = "creation_date", nullable = false ,updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
    private Timestamp createdAt;

    @UpdateTimestamp // for auditing
    @Column(name = "last_update_date",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
    private Timestamp updatedAt;

    @Version // locking //
    @JsonIgnore
    public Long version;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "job_status")
    private JobStatus jobStatus;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
    @Column(name = "job_execution_time")
    private LocalDateTime jobExecutionTime;

    @Column(name = "completed_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
    private LocalDateTime completedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "job_priority")
    private JobPriority jobPriority;

    @Column(name="job_type", nullable=false, updatable=false, insertable=false)
    private String jobType;

    @Lob
    @Column(name = "notes")
    private String note;

    // for all sub classes
    public Job(LocalDateTime jobExecutionTime, JobPriority jobPriority) {
        this.jobExecutionTime = jobExecutionTime;
        this.jobPriority = jobPriority;
        this.jobStatus = JobStatus.QUEUED;
    }

    public Job(String jobType, LocalDateTime jobExecutionTime, JobPriority jobPriority){
        this(jobExecutionTime, jobPriority);
        this.jobType = jobType;
        this.jobStatus = JobStatus.QUEUED;
    }
}
