package com.app.restserver.repositories;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RabbitJobRepository {
    private final Queue messageQueue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitJobRepository(Queue messageQueue, RabbitTemplate rabbitTemplate) {
        this.messageQueue = messageQueue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void putToQueue(String job) {
        rabbitTemplate.convertAndSend(messageQueue.getName(), job);
    }
}
