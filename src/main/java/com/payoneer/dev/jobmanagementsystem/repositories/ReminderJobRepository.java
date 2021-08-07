package com.payoneer.dev.jobmanagementsystem.repositories;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReminderJobRepository extends JpaRepository<ReminderJob, UUID> {

    Optional<ReminderJob> findOneByJobId(UUID ID);
    void deleteReminderJobByJobId(UUID id);
    List<ReminderJob> findAllByJobStatusAndExecutionTimeBetween(JobStatus status, LocalDateTime from, LocalDateTime to);
}
