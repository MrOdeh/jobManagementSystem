package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;

public interface ReminderJobService {

    ReminderJob findById(String id);

    ReminderJob save(ReminderJob job);

    void deleteById(String id);
}
