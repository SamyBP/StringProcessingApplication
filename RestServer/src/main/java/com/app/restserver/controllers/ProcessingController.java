package com.app.restserver.controllers;

import com.app.restserver.dtos.Job;
import com.app.restserver.dtos.TargetResponse;
import com.app.restserver.services.JobSenderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ProcessingController {
    private final JobSenderService jobSenderService;
    private final Logger logger = LoggerFactory.getLogger(ProcessingController.class);

    @Autowired
    public ProcessingController(JobSenderService jobSenderService) {
        this.jobSenderService = jobSenderService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/v1/jobs")
    public ResponseEntity<?> handleV1JobRequest(@RequestBody @Valid Job job) {
        logger.info(String.format("[V1] received job:%s", job));
        jobSenderService.sendToRabbitQueue(job);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/v2/jobs")
    public ResponseEntity<?> handleV2JobRequest(@RequestBody @Valid Job job) {
        logger.info(String.format("[V2] received job:%s", job));
        jobSenderService.sendToPostgresQueue(job);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/callback")
    public ResponseEntity<?> testCallback(@RequestBody TargetResponse targetResponse) {
        logger.info(String.format("Received response:%s", targetResponse));
        return ResponseEntity.ok().build();
    }
}
