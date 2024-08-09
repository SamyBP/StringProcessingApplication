package com.app.restserver.controllers;

import com.app.restserver.dtos.Job;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessingController {

    @RequestMapping(method = RequestMethod.POST, value = "jobs")
    public ResponseEntity<?> handleJobRequest(@RequestBody @Valid Job job) {
        System.out.println(job);
        return ResponseEntity.accepted().build();
    }
}
