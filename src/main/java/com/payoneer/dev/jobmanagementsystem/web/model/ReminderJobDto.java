package com.payoneer.dev.jobmanagementsystem.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReminderJobDto {

    private String reminderDescription;

    private String mobileNumber;
}
