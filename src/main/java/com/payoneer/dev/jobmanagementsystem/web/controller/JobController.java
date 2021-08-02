package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.payoneer.dev.jobmanagementsystem.services.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/job")
@Tag(name = "Job")
public class JobController {

    private final JobService jobService;

    @GetMapping(value = "/")
    public ResponseEntity<Object> getAllJobs(@RequestParam(value="key", required = false, defaultValue = "name") String key){

        if(key.equals("all")) {

            return new ResponseEntity(jobService.findAll(), HttpStatus.OK);
        }else if(key.equals("processed")){

            return new ResponseEntity(jobService.findAllProcessedJobs(), HttpStatus.OK);
        }else if(key.equals("unprocessed")){

            return new ResponseEntity(jobService.findAllQueuedJobs(), HttpStatus.OK);
        }else if(key.equals("email")){

            return new ResponseEntity(jobService.findAllEmailJobs(), HttpStatus.OK);
        }else if(key.equals("reminder")){

            return new ResponseEntity(jobService.findAllReminderJobs(), HttpStatus.OK);
        }

        // default is to return job Names only
        return new ResponseEntity(jobService.findAllJobNames(), HttpStatus.OK);
    }



}
