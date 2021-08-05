package com.payoneer.dev.jobmanagementsystem.web.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailJobDto {

    private String messageBody;

    private String sender;

    private String receiver;
}
