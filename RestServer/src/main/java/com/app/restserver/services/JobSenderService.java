package com.app.restserver.services;

import com.app.restserver.dtos.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSenderService {
    private final Queue messageQueue;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public JobSenderService(Queue messageQueue, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.messageQueue = messageQueue;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public void send(Job job) {
        String jobJson = objectMapper.writeValueAsString(job);
        rabbitTemplate.convertAndSend(messageQueue.getName(), jobJson);
    }
}
