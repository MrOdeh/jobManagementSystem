package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;

public interface EmailJobService {

    EmailJob findById(String id);

    EmailJob save(EmailJob job, boolean schedule);

    void deleteById(String id);
}
