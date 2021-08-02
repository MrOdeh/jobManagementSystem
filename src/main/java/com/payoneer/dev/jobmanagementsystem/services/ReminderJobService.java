package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;

import java.util.List;

public interface ReminderJobService {

    ReminderJob findById(String id);

    List<ReminderJob> findAll();

    ReminderJob save(ReminderJob job, boolean schedule);

    void deleteById(String id);

    List<ReminderJob> saveAll(List<ReminderJob> jobs, boolean schedule);
}
