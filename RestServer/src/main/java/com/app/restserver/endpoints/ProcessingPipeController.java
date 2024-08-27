package com.app.restserver.endpoints;

import com.app.restserver.authentication.Authenticated;
import com.app.restserver.authentication.FromToken;
import com.app.restserver.endpoints.requests.PipeCreationRequest;
import com.app.restserver.endpoints.requests.PipeUpdateRequest;
import com.app.restserver.endpoints.util.EndpointsUtil;
import com.app.restserver.entities.Pipe;
import com.app.restserver.services.ProcessingPipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = EndpointsUtil.PIPE)
public class ProcessingPipeController {

    private final ProcessingPipeService processingPipeService;

    @Autowired
    public ProcessingPipeController(ProcessingPipeService processingPipeService) {
        this.processingPipeService = processingPipeService;
    }

    //TODO return the newly created entity
    @Authenticated
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@FromToken Long userId, @RequestBody @Valid PipeCreationRequest pipeCreationRequest) {
        processingPipeService.create(userId, pipeCreationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Authenticated
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Pipe>> getAll(@FromToken Long userId) {
        List<Pipe> pipesCreatedByUser = processingPipeService.getAll(userId);
        return new ResponseEntity<>(pipesCreatedByUser, HttpStatus.OK);
    }

    @Authenticated
    @RequestMapping(method = RequestMethod.GET, value = "/{pipe_id}")
    public ResponseEntity<?> getByPipeId(@PathVariable(value = "pipe_id") Long pipeId) {
        Pipe pipe = processingPipeService.getById(pipeId);
        return new ResponseEntity<>(pipe, HttpStatus.OK);
    }

    @Authenticated
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updatePipeDefinition(@RequestBody PipeUpdateRequest pipeUpdateRequest, @FromToken Long userId) {
        Pipe pipe = processingPipeService.updatePipeDefinition(pipeUpdateRequest, userId);
        return new ResponseEntity<>(pipe, HttpStatus.CREATED);
    }

    @Authenticated
    @RequestMapping(method = RequestMethod.DELETE, value = "/{pipe_id}")
    public ResponseEntity<?> remove(@FromToken Long userId, @PathVariable(value = "pipe_id") Long pipeId) {
        processingPipeService.remove(userId, pipeId);
        return ResponseEntity.noContent().build();
    }
}
