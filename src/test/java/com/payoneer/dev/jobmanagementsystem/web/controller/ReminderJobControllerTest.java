package com.payoneer.dev.jobmanagementsystem.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.enumeration.JobPriority;
import com.payoneer.dev.jobmanagementsystem.services.ReminderJobServiceImpl;
import com.payoneer.dev.jobmanagementsystem.services.ReminderJobServiceImplTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReminderJobController.class)
class ReminderJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReminderJobServiceImpl reminderJobService;
    ReminderJob reminderJob;

    ReminderJobController controller;

    @BeforeEach
    public void setup(){
        reminderJob = reminderJob = ReminderJob.builder()
                .reminderDescription("nothing")
                .jobPriority(JobPriority.LOW)
                .jobExecutionTime(LocalDateTime.now())
                .mobileNumber("nothing")
                .build();
    }

    @Test
    void getById() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(reminderJobService.findById(id)).thenReturn(reminderJob);
        mockMvc.perform(get("http://localhost:8080/v1/reminderjob/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post("http://localhost:8080/v1/reminderjob/")
                .content(asJsonString(reminderJob))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteById() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(reminderJobService.findById(id)).thenReturn(reminderJob);
        mockMvc.perform(delete("http://localhost:8080/v1/reminderjob/" + id))
                .andExpect(status().isOk());
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