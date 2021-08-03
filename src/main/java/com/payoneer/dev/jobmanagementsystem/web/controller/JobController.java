package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/job")
@Tag(name = "Job")
public class JobController {

    private final JobService jobService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllJobs(){
        return new ResponseEntity(jobService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/type")
    public ResponseEntity<Object> getAllJobsByType(@RequestParam(value="type", required = true, defaultValue = "email")String key){
        return new ResponseEntity(jobService.findAllJobsByType(key), HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Object> getAllJobsByStatus(@RequestParam(value="status", required = false, defaultValue = "QUEUED")JobStatus key){
        return new ResponseEntity(jobService.findAllJobsByStatus(key) , HttpStatus.OK);
    }

    @GetMapping(value = "/availableTypes")
    public ResponseEntity<List<String>> getAllJobsTypes(){
        return new ResponseEntity(jobService.findAllJobsNames(), HttpStatus.OK);
    }
}
