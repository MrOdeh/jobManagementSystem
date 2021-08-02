package com.payoneer.dev.jobmanagementsystem.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum JobPriority {

    LOW(3),
    MEDIUM(2),
    HIGH(1);

    private Integer value;

}
