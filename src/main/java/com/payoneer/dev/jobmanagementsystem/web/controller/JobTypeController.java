package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import com.payoneer.dev.jobmanagementsystem.services.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("v1/jobtype")
public class JobTypeController {

    private final JobTypeService jobTypeService;

    @Deprecated
    @GetMapping("/id/{id}")
    public JobType getById(@PathVariable String id){
        return jobTypeService.findById(id);
    }

    @GetMapping("/name/{name}")
    public JobType getByName(@PathVariable String name){
        return jobTypeService.findByJobName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public JobType create(@RequestBody JobType job) {
        return jobTypeService.save(job);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/id/{id}")
    public void deleteById(String id){
        jobTypeService.deleteById(id);
    }
}
