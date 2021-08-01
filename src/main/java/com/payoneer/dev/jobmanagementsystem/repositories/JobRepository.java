package com.payoneer.dev.jobmanagementsystem.repositories;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findAllByJobStatusAndJobExecutionTimeBetween(JobStatus status, LocalDateTime now, LocalDateTime after);
}
