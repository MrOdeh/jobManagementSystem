package com.payoneer.dev.jobmanagementsystem.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public class JobEvent<T> implements Serializable {

    public static final long serialVersionUID = 22L;
    private T event;
    private String jobType;
}
