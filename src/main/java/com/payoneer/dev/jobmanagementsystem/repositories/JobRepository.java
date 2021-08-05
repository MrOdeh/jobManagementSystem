package com.payoneer.dev.jobmanagementsystem.repositories;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findAllByJobStatusAndExecutionTimeBetween(JobStatus status, LocalDateTime now, LocalDateTime after);

    List<Job> findAllByJobType(String type);

    List<Job> findAllByJobStatus(JobStatus status);

    @Query("SELECT E from Job E where E.jobStatus in :status")
    List<Job> findAllProccessdJobs(@Param("status") List<JobStatus> statusList);

}
