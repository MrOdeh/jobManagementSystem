package com.payoneer.dev.jobmanagementsystem.repositories;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReminderJobRepository extends JpaRepository<ReminderJob, UUID> {

    Optional<ReminderJob> findOneByJobId(UUID ID);
    void deleteReminderJobByJobId(UUID id);
}
