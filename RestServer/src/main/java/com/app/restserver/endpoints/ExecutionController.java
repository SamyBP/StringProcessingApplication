package com.app.restserver.endpoints;

import com.app.restserver.authentication.Authenticated;
import com.app.restserver.authentication.FromToken;
import com.app.restserver.endpoints.responses.ExecutionHistoryResponse;
import com.app.restserver.endpoints.requests.PipeExecutionRequest;
import com.app.restserver.endpoints.util.EndpointsUtil;
import com.app.restserver.dtos.ProcessingPipe;
import com.app.restserver.endpoints.responses.CallbackResponse;
import com.app.restserver.services.ExecutionService;
import com.app.restserver.services.QueueDispatcherService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = EndpointsUtil.EXECUTION)
public class ExecutionController {
    private final QueueDispatcherService queueDispatcherService;
    private final ExecutionService executionService;
    private final Logger logger = LoggerFactory.getLogger(ExecutionController.class);

    @Value("${execution.callback.url}")
    private String callbackURL;

    @Autowired
    public ExecutionController(QueueDispatcherService queueDispatcherService, ExecutionService executionService) {
        this.queueDispatcherService = queueDispatcherService;
        this.executionService = executionService;
    }

    @Authenticated
    @RequestMapping(method = RequestMethod.POST, value = "/private")
    public ResponseEntity<?> handlePrivateExecution(@RequestBody @Valid PipeExecutionRequest pipeExecutionRequest, @FromToken Long userId) {
        logger.info(String.format("Executing pipe:%d", pipeExecutionRequest.getPipeId()));
        Long executionId =  executionService.persistExecution(pipeExecutionRequest, userId);
        ProcessingPipe processingPipe = createProcessingPipeFromRequest(pipeExecutionRequest, executionId);
        queueDispatcherService.send(processingPipe);
        return ResponseEntity.accepted().build();
    }

    private ProcessingPipe createProcessingPipeFromRequest(PipeExecutionRequest request, Long executionId) {
        return ProcessingPipe.builder()
                .id(executionId)
                .input(request.getInput())
                .version(request.getVersion())
                .modules(request.getModules())
                .callback(callbackURL)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/public")
    public ResponseEntity<?> handlePublicJobRequest(@RequestBody @Valid ProcessingPipe processingPipe) {
        logger.info(String.format("Received processing pipe:%s", processingPipe));
        queueDispatcherService.send(processingPipe);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/callback")
    public ResponseEntity<?> testCallback(@RequestBody CallbackResponse callbackResponse) {
        logger.info(String.format("Received response:%s", callbackResponse));
        executionService.processCallbackResponse(callbackResponse);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getExecutionHistory(@FromToken Long userId) {
        logger.info(String.format("Retrieving execution history for user:%d", userId));
        List<ExecutionHistoryResponse> executionHistory = executionService.getUsersExecutionHistory(userId);
        return new ResponseEntity<>(executionHistory, HttpStatus.OK);
    }
}
