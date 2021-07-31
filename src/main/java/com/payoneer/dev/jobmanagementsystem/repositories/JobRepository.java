package com.payoneer.dev.jobmanagementsystem.repositories;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {

}
