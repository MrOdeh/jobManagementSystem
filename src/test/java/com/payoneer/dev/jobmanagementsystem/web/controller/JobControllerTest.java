package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.services.impl.JobServiceImpl;
import com.payoneer.dev.jobmanagementsystem.services.impl.ReminderJobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JobController.class)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobServiceImpl jobService;

    Job reminder;

    Job email;

    LocalDateTime now;

    JobController controller;
    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        reminder =  ReminderJob.builder()
                .reminderDescription("nothing")
                .jobPriority(JobPriority.LOW)
                .jobExecutionTime(now)
                .mobileNumber("30303030")
                .build();

        email = EmailJob.builder()
                .jobExecutionTime(now)
                .messageBody("nothing")
                .sender("aksjkasj@payoneer.com")
                .receiver("aksjkasj2@payoneer.com")
                .jobPriority(JobPriority.MEDIUM)
                .build();
    }

    @Test
    void getAllJobs() throws Exception {
        List<Job> jobs = new ArrayList<>();
        jobs = prepareJobs(jobs);

        Mockito.when(jobService.findAll()).thenReturn(jobs);
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/v1/job/?key=all"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(asJsonString(contentAsString));
    }
    public List<Job> prepareJobs(List<Job> jobs){

        reminder =  ReminderJob.builder()
                .reminderDescription("nothing")
                .jobPriority(JobPriority.LOW)
                .jobExecutionTime(now)
                .mobileNumber("30303030")
                .build();

        email = EmailJob.builder()
                .jobExecutionTime(now)
                .messageBody("nothing")
                .sender("aksjkasj@payoneer.com")
                .receiver("aksjkasj2@payoneer.com")
                .jobPriority(JobPriority.MEDIUM)
                .build();
        jobs.add(reminder);
        jobs.add(email);
        return jobs;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asStringJson(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return  mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}