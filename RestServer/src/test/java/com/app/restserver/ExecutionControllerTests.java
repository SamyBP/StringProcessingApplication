package com.app.restserver;

import com.app.restserver.endpoints.ExecutionController;
import com.app.restserver.dtos.ProcessingPipe;
import com.app.restserver.dtos.ModuleDto;
import com.app.restserver.dtos.ModuleName;
import com.app.restserver.services.QueueDispatcherService;
import com.app.restserver.endpoints.util.EndpointsUtil;
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

@WebMvcTest(controllers = ExecutionController.class)
@ExtendWith(MockitoExtension.class)
public class ExecutionControllerTests {
    @MockBean
    QueueDispatcherService queueDispatcherService;
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    ModuleDto givenT1;
    ModuleDto givenT2;
    ProcessingPipe givenProcessingPipe;

    @BeforeEach
    public void initialize() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("start", 1);
        args.put("end", 2);

        givenT1 = ModuleDto.builder()
                .name(ModuleName.SUBSTRING)
                .args(args)
                .build();

        givenT2 = ModuleDto.builder()
                .name(ModuleName.LOWER)
                .args(new HashMap<>())
                .build();


        givenProcessingPipe = ProcessingPipe.builder()
                .id(1L)
                .callback("http://localhost:8001/callback")
                .input("some input string")
                .modules(Arrays.asList(givenT1, givenT2))
                .build();
    }

    @Test
    public void whenNullId_statusCodeIsBadRequest() {
        givenProcessingPipe.setId(null);

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenCallbackIsNotValid_statusCodeIsBadRequest() {
        givenProcessingPipe.setCallback("invalid url");

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenInputIsNull_statusCodeIsBadRequest() {
        givenProcessingPipe.setInput(null);

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenInputIsEmpty_statusCodeIsBadRequest() {
        givenProcessingPipe.setInput("");

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenTasksIsNull_statusCodeIsBadRequest() {
        givenProcessingPipe.setModules(null);

        performPostAndExpectBadRequest();
    }

    @Test
    public void whenTaskTypeNull_statusCodeIsBadRequest() {
        givenT1.setName(null);

        performPostAndExpectBadRequest();
    }

    private void performPostAndExpectBadRequest() {
        Assertions.assertDoesNotThrow(() -> {
            mockMvc.perform(post(EndpointsUtil.EXECUTION + "/public/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(givenProcessingPipe)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        });
    }

    @Test
    public void whenValidInput_statusCodeIsAccepted() {
        Assertions.assertDoesNotThrow(() -> {
            mockMvc.perform(post(EndpointsUtil.EXECUTION + "/public/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(givenProcessingPipe)))
                    .andExpect(MockMvcResultMatchers.status().isAccepted());
        });
    }
}
