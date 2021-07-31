package com.payoneer.dev.jobmanagementsystem.exception;

import org.springframework.http.HttpStatus;

public class GenericClientException extends RuntimeException {

    private String exceptionMessage;
    public GenericClientException(String message, HttpStatus badRequest) {
        super(message);
    }

    public GenericClientException(String message) {
    }
}
