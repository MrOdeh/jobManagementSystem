package com.payoneer.dev.jobmanagementsystem.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class InvalidUserInput {

    private Integer status;
    private String message;
    private Long timeStamp;

}
