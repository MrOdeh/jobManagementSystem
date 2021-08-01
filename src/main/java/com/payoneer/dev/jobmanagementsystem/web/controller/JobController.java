package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("v1/job")
public class JobController {

    private final JobService jobService;

    @GetMapping(value = "/")
    public ResponseEntity<Job> getAll(){
        return new ResponseEntity(jobService.findAllJobNames(), HttpStatus.OK);
    }
}
