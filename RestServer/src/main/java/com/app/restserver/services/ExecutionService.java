package com.app.restserver.services;

import com.app.restserver.endpoints.responses.CallbackResponse;
import com.app.restserver.endpoints.responses.ExecutionHistoryResponse;
import com.app.restserver.dtos.ModuleDto;
import com.app.restserver.endpoints.requests.PipeExecutionRequest;
import com.app.restserver.entities.*;
import com.app.restserver.persistance.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ExecutionService {
    private final ExecutionRepository executionRepository;
    private final ExecutionModuleRepository executionModuleRepository;
    private final UserRepository userRepository;
    private final PipeRepository pipeRepository;
    private final ObjectMapper objectMapper;

    public ExecutionService(
            ExecutionRepository executionRepository,
            ExecutionModuleRepository executionModuleRepository,
            UserRepository userRepository,
            PipeRepository pipeRepository,
            ObjectMapper objectMapper
    ) {
        this.executionRepository = executionRepository;
        this.executionModuleRepository = executionModuleRepository;
        this.userRepository = userRepository;
        this.pipeRepository = pipeRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Long persistExecution(PipeExecutionRequest request, Long userId) {
        Pipe pipe = pipeRepository.getReferenceById(request.getPipeId());
        checkUserCanExecutePipe(userId, pipe, pipe.getUser());
        User authenticatedUser = userRepository.getReferenceById(userId);
        Execution execution = new Execution(authenticatedUser, pipe, request.getInput(), request.getVersion());
        execution = executionRepository.save(execution);
        setupExecutionDetails(execution, request.getModules());
        return execution.getId();
    }

    private void checkUserCanExecutePipe(Long authenticatedUserId, Pipe pipe, User pipeOwner) {
        if (!pipeOwner.getId().equals(authenticatedUserId) && !pipe.isPublic())
            throw new NotAuthorizedException(String.format("User:%d cannot execute pipe:%d", authenticatedUserId, pipe.getId()));
    }

    private void setupExecutionDetails(final Execution execution, List<ModuleDto> moduleDtoList) {
        moduleDtoList.forEach(moduleDto -> {
            String args = convertArgumentsToJsonString(moduleDto.getArgs());
            String moduleName = moduleDto.getName().toString();
            ExecutionModule executionModule = new ExecutionModule(execution, moduleName, args);
            executionModuleRepository.save(executionModule);
        });
    }

    @SneakyThrows
    private String convertArgumentsToJsonString(Map<String, Object> arguments) {
        return objectMapper.writeValueAsString(arguments);
    }

    public List<ExecutionHistoryResponse> getUsersExecutionHistory(Long userId) {
        List<Execution> executions = executionRepository.findAllByUserId(userId);
        return executions.stream()
                .map(computeExecutionMapperFunction())
                .toList();
    }

    private Function<Execution, ExecutionHistoryResponse> computeExecutionMapperFunction() {
        return executionLog -> ExecutionHistoryResponse.builder()
                .createdAt(executionLog.getCreatedAt())
                .startedAt(executionLog.getStartedAt())
                .endedAt(executionLog.getEndedAt())
                .pipeName(executionLog.getPipe().getName())
                .version(executionLog.getVersion())
                .input(executionLog.getInput())
                .modules(executionLog.getExecutionModules())
                .result(executionLog.getResult())
                .status(executionLog.getStatus())
                .build();
    }

    @Transactional
    public void processCallbackResponse(CallbackResponse response) {
        Execution execution = executionRepository.findByIdIfExists(response.getId());
        execution.setResult(response.getResult());
        execution.setStartedAt(convertTimestamp(response.getStartedAt()));
        execution.setEndedAt(convertTimestamp(response.getEndedAt()));
        execution.setStatus(computeStatusString(response.getIsSuccess()));
        executionRepository.save(execution);
    }

    private LocalDateTime convertTimestamp(Long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault()
        );
    }

    private String computeStatusString(boolean isSuccess) {
        return isSuccess ? "FINISHED" : "ERROR";
    }
}
