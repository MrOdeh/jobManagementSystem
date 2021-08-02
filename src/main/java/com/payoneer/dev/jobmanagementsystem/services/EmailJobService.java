package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;

import java.util.List;
import java.util.stream.Stream;

public interface EmailJobService {

    EmailJob findById(String id);

    List<EmailJob> findAll();

    EmailJob save(EmailJob job, boolean schedule);

    void deleteById(String id);

    List<EmailJob> saveAll(List<EmailJob> jobs, boolean schedule);
}
