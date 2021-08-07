package com.payoneer.dev.jobmanagementsystem.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class JobEvent<T> {

    private T event;
    private String jobType;
}
