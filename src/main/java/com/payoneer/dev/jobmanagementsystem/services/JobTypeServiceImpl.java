package com.payoneer.dev.jobmanagementsystem.services;

import com.payoneer.dev.jobmanagementsystem.domain.JobType;
import com.payoneer.dev.jobmanagementsystem.repositories.JobTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
@Service
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeRepository jobTypeRepository;

    @Deprecated
    @Override
    public JobType findById(String id) {
        Optional<JobType> byId = jobTypeRepository.findById(UUID.fromString(id));

        if(!byId.isPresent()){
            log.error("invlaid User Input");
            return new JobType();
        }

        return byId.get();

    }

    @Override
    public JobType findByJobName(String name) {
        if(name == null || "".equals(name)){
            throw new RuntimeException("invalid input");
        }
        Optional<JobType> byId = jobTypeRepository.findJobTypeByJobName(name);

        if(!byId.isPresent()){
            log.error("invlaid User Input");
            return new JobType();
        }

        return byId.get();

    }

    @Override
    public JobType save(JobType job) {
        return jobTypeRepository.save(job);
    }

    @Override
    public JobType update(JobType job) {
        if(job == null){
            throw new RuntimeException("invalid input");
        }

        return jobTypeRepository.save(job);
    }

    @Override
    public void deleteById(String id) {
        jobTypeRepository.deleteById(UUID.fromString(id));
    }
}
