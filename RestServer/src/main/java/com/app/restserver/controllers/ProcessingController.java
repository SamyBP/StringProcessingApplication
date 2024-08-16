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
public class ProcessingController {
    private final JobSenderService jobSenderService;
    private final Logger logger = LoggerFactory.getLogger(ProcessingController.class);

    @Autowired
    public ProcessingController(JobSenderService jobSenderService) {
        this.jobSenderService = jobSenderService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "jobs")
    public ResponseEntity<?> handleJobRequest(@RequestBody @Valid Job job) {
        logger.info(String.format("Received job:%s", job));
        jobSenderService.send(job);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "callback")
    public ResponseEntity<?> testCallback(@RequestBody TargetResponse targetResponse) {
        logger.info(String.format("Received response:%s", targetResponse));
        return ResponseEntity.ok().build();
    }
}
