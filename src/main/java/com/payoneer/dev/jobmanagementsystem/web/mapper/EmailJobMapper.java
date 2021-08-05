package com.payoneer.dev.jobmanagementsystem.web.mapper;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.web.model.EmailJobDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface EmailJobMapper {

    EmailJobDto emailJobToEmailJobDto(EmailJob job);

    EmailJob emailJobDtoToEmail(EmailJobDto job);
}
