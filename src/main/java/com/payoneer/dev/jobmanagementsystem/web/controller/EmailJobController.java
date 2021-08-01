package com.payoneer.dev.jobmanagementsystem.web.controller;


import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/emailjob")
public class EmailJobController {

    private final EmailJobService emailJobService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmailJob> getById(@PathVariable String id){
        return ResponseEntity.ok(emailJobService.findById(id));
    }

    @PostMapping(value = "/")
    public ResponseEntity<EmailJob> save(@RequestBody EmailJob job,
                                        @RequestParam(value="schedule", required = false, defaultValue = "false") boolean schedule){
        return new ResponseEntity(emailJobService.save(job, schedule), HttpStatus.CREATED);
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
