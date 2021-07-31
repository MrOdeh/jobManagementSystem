package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface EmailJobService {

    EmailJob findById(String id);

    EmailJob save(EmailJob job);

    void deleteById(String id);
}
