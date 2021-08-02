package com.payoneer.dev.jobmanagementsystem.web.controller;


import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/emailjob")
@Tag(name = "Email")
public class EmailJobController {

    private final EmailJobService emailJobService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmailJob> getById(@PathVariable String id){
        return ResponseEntity.ok(emailJobService.findById(id));
    }

    @GetMapping(value = "/bulk")
    public ResponseEntity<List<EmailJob>> getAll(){
        return ResponseEntity.ok(emailJobService.findAll());
    }

    @PostMapping(value = "/")
    public ResponseEntity<EmailJob> save(@RequestBody EmailJob job,
                                        @RequestParam(value="schedule", required = false, defaultValue = "false") boolean schedule){
        return new ResponseEntity(emailJobService.save(job, schedule), HttpStatus.CREATED);
    }

    @PostMapping(value = "/bulk")
    public ResponseEntity<EmailJob> saveAll(@RequestBody List<EmailJob> jobs,
                                         @RequestParam(value="schedule", required = false, defaultValue = "false") boolean schedule){
        return new ResponseEntity(emailJobService.saveAll(jobs, schedule), HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    public ResponseEntity<EmailJob> update(@RequestBody EmailJob job,
                                           @RequestParam(value="schedule", required = false, defaultValue = "false") boolean schedule){
        return new ResponseEntity(emailJobService.save(job, schedule), HttpStatus.ACCEPTED);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable String id){
        emailJobService.deleteById(id);
    }

}
