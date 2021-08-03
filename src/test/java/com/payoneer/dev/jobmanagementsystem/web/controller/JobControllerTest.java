package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobStatus;
import com.payoneer.dev.jobmanagementsystem.services.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JobController.class)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
    void getAll() throws Exception {
        List<Job> jobs = new ArrayList<>();
        jobs = prepareJobs(jobs);

        Mockito.when(jobService.findAll()).thenReturn(jobs);
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/v1/job"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<Job> myObjects = Arrays.asList(objectMapper.readValue(contentAsString, Job[].class));
        assertTrue(myObjects.get(0).getJobStatus().equals(jobs.get(0).getJobStatus()));
    }

    @Test
    void getAllJobsTypes() throws Exception {
        List<Job> jobs = new ArrayList<>();
        jobs = prepareJobs(jobs);

        List<String> availableJobs = Arrays.asList("email", "reminder");
        Mockito.when(jobService.findAllJobsNames()).thenReturn(availableJobs);
        mockMvc.perform(get("http://localhost:8080/v1/job/availableTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(availableJobs.toString()));

    }

    @Test
    void getAllJobsByStatus_UNPROCCESSED() throws Exception {
        List<Job> jobs = new ArrayList<>();
        jobs = prepareJobs(jobs);
        jobs.get(0).setJobStatus(JobStatus.QUEUED);
        jobs.get(1).setJobStatus(JobStatus.QUEUED);

        Mockito.when(jobService.findAllJobsByStatus(JobStatus.QUEUED)).thenReturn(jobs);
        MvcResult mvcResult =  mockMvc.perform(get("http://localhost:8080/v1/job/status?status=QUEUED"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Job> myObjects = Arrays.asList(objectMapper.readValue(contentAsString, Job[].class));
        assertTrue(myObjects.get(0).getJobStatus().equals(jobs.get(0).getJobStatus()));


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

}