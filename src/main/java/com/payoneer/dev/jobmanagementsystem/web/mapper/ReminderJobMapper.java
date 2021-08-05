package com.payoneer.dev.jobmanagementsystem.web.mapper;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.web.model.ReminderJobDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ReminderJobMapper {

    ReminderJobDto reminderJobToReminderJobDto(ReminderJob reminderJob);

    ReminderJob reminderJobDtoToReminderJob(ReminderJobDto reminderJobDto);

}
