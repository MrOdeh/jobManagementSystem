package com.payoneer.dev.jobmanagementsystem.web.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailJobDto {

    private String messageBody;

    private String messageSubject;

    private String sender;

    private String recipients;

    private Boolean isHtml;

    private String attachmentPath;

    private List<String> ccList;

    private List<String> bccList;
}
