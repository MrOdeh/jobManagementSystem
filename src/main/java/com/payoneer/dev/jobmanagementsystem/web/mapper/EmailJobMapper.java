package com.payoneer.dev.jobmanagementsystem.web.mapper;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.web.model.EmailJobDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Mapper
public interface EmailJobMapper {

    @Mapping( target = "bccList", source = "bccList")
    @Mapping( target = "ccList", source = "ccList")
    EmailJobDto emailJobToEmailJobDto(EmailJob job);

    @Mapping( target = "bccList", source = "bccList")
    @Mapping( target = "ccList", source = "ccList")
    EmailJob emailJobDtoToEmail(EmailJobDto job);

    default List<String> mapStringToStringList(String bcc){
        return Arrays.asList(bcc.split(","));
    }

    default String mapStringListToString(List<String> bcc){
        return String.join(",", bcc);
    }
}
