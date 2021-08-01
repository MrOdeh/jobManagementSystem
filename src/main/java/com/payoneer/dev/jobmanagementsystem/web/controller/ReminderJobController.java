package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.services.ReminderJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/reminderjob")
public class ReminderJobController {

    private final ReminderJobService reminderJobService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<ReminderJob> getById(@PathVariable String id){
        return ResponseEntity.ok(reminderJobService.findById(id));
    }

    @PostMapping(value = "/")
    public ResponseEntity<ReminderJob> save(@RequestBody ReminderJob job,
                                            @RequestParam(value="schedule", required = false, defaultValue = "false") boolean schedule){
        return new ResponseEntity(reminderJobService.save(job,schedule), HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    public ResponseEntity<ReminderJob> update(@RequestBody ReminderJob job,
                                              @RequestParam(value="schedule", required = false, defaultValue = "false") boolean schedule){
        return new ResponseEntity(reminderJobService.save(job, schedule), HttpStatus.ACCEPTED);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable String id){
        reminderJobService.deleteById(id);
    }


}
