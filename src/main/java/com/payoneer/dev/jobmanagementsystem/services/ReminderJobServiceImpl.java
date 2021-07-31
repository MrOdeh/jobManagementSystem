package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.repositories.ReminderJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReminderJobServiceImpl implements ReminderJobService {

    private final ReminderJobRepository reminderJobRepository;

    @Override
    public ReminderJob findById(String id) {
        try {
            return reminderJobRepository.findOneByJobId(UUID.fromString(id)).orElseThrow(() -> new GenericClientException("Email job not found for# " + id, HttpStatus.BAD_REQUEST ));
        }catch (NullPointerException ne){
            log.error("invalid user input");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ReminderJob save(ReminderJob job) {
        return reminderJobRepository.save(job);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        try{
            reminderJobRepository.deleteReminderJobByJobId(UUID.fromString(id));
        }catch (NullPointerException ne){
            log.error("Invalid User Input");
        }catch (IllegalArgumentException e){
            log.error("invalid UUID");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }
}
