package com.payoneer.dev.jobmanagementsystem.repositories;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailJobRepository extends JpaRepository<EmailJob, UUID> {

    Optional<EmailJob> findOneByJobId(UUID id);
    void deleteEmailJobByJobId(UUID id);
    List<EmailJob> findAllByJobStatusAndExecutionTimeBetween(JobStatus status, LocalDateTime from, LocalDateTime to);
}
