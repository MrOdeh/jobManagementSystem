package com.payoneer.dev.jobmanagementsystem.repositories;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobTypeRepository extends JpaRepository<JobType, UUID> {

    Optional<JobType> findById(UUID id);
    Optional<JobType> findJobTypeByJobName (String jobName);
}
