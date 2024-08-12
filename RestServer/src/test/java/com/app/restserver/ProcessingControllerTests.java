package com.app.restserver;

import com.app.restserver.controllers.ProcessingController;
import com.app.restserver.dtos.Job;
import com.app.restserver.dtos.Task;
import com.app.restserver.dtos.TaskType;
import com.app.restserver.services.JobSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ProcessingController.class)
@ExtendWith(MockitoExtension.class)
public class ProcessingControllerTests {
    @MockBean JobSenderService jobSenderService;
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    Task givenT1;
    Task givenT2;
    Job givenJob;

    @BeforeEach
    public void initialize() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("start", 1);
        args.put("end", 2);

        givenT1 = Task.builder()
                .type(TaskType.SUBSTRING)
                .args(args)
                .build();

        givenT2 = Task.builder()
                .type(TaskType.LOWER)
                .args(new HashMap<>())
                .build();


        givenJob = Job.builder()
                .id("f90c75dc-b581-4385-9184-1bf18e2bf805")
                .callback("http://localhost:8001/callback")
                .input("some input string")
                .tasks(Arrays.asList(givenT1, givenT2))
                .build();
    }

    @Test
    public void whenNullId_statusCodeIsBadRequest() {
        givenJob.setId(null);

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenEmptyId_statusCodeIsBadRequest() {
        givenJob.setId("");

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenBlankId_statusCodeIsBadRequest() {
        givenJob.setId(" ");

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenCallbackIsNotValid_statusCodeIsBadRequest() {
        givenJob.setCallback("invalid url");

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenInputIsNull_statusCodeIsBadRequest() {
        givenJob.setInput(null);

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenInputIsEmpty_statusCodeIsBadRequest() {
        givenJob.setInput("");

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenTasksIsNull_statusCodeIsBadRequest() {
        givenJob.setTasks(null);

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenTaskTypeNull_statusCodeIsBadRequest() {
        givenT1.setType(null);

        performPostAndExpectBadRequest();
    }

    private void performPostAndExpectBadRequest() {
        Assertions.assertDoesNotThrow(() -> {
            mockMvc.perform(post("/jobs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(givenJob)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        });
    }

    @Test
    public void whenValidInput_statusCodeIsAccepted() {
        Assertions.assertDoesNotThrow(() -> {
            mockMvc.perform(post("/jobs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(givenJob)))
                    .andExpect(MockMvcResultMatchers.status().isAccepted());
        });
    }
}
