package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.exception.GenericClientException;
import com.payoneer.dev.jobmanagementsystem.repositories.EmailJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailJobServiceImpl implements EmailJobService {

    private final EmailJobRepository emailJobRepository;

    @Override
    public EmailJob findById(String id) {
        try {
            return emailJobRepository.findOneByJobId(UUID.fromString(id)).orElseThrow(() -> new GenericClientException("Email job not found for# " + id, HttpStatus.BAD_REQUEST ));
        }catch (NullPointerException ne){
            log.error("invalid user input");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public EmailJob save(EmailJob job) {
        return emailJobRepository.save(job);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        try{
            emailJobRepository.deleteEmailJobByJobId(UUID.fromString(id));
        }catch (NullPointerException ne){
            log.error("Invalid User Input");
        }catch (IllegalArgumentException e){
            log.error("invalid UUID");
            throw new GenericClientException("Invalid User Input", HttpStatus.BAD_REQUEST);
        }
    }
}
