package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.exception.InvalidUserInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice  {


    @ExceptionHandler(value = {GenericClientException.class, RuntimeException.class})
    public ResponseEntity<InvalidUserInput> resourceNotFoundException(GenericClientException ex, WebRequest request) {

        return new ResponseEntity<InvalidUserInput>(InvalidUserInput.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build(), HttpStatus.NOT_FOUND);
    }
}
