package com.app.restserver.services;

import com.app.restserver.dtos.Job;
import com.app.restserver.repositories.PostgresJobRepository;
import com.app.restserver.repositories.RabbitJobRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSenderService {
    private final RabbitJobRepository rabbitJobRepository;
    private final PostgresJobRepository postgresJobRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public JobSenderService(RabbitJobRepository rabbitJobRepository, PostgresJobRepository postgresJobRepository, ObjectMapper objectMapper) {
        this.rabbitJobRepository = rabbitJobRepository;
        this.postgresJobRepository = postgresJobRepository;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public void sendToRabbitQueue(Job job) {
        String jobJson = objectMapper.writeValueAsString(job);
        rabbitJobRepository.putToQueue(jobJson);
    }

    @SneakyThrows
    public void sendToPostgresQueue(Job job) {
        String jobJson = objectMapper.writeValueAsString(job);
        postgresJobRepository.putToQueue(jobJson);

    }
}
