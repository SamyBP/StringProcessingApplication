package com.app.restserver.persistance.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitQueueDispatcherStrategy implements QueueDispatcherStrategy {
    private final Queue messageQueue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitQueueDispatcherStrategy(Queue messageQueue, RabbitTemplate rabbitTemplate) {
        this.messageQueue = messageQueue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void put(String job) {
        rabbitTemplate.convertAndSend(messageQueue.getName(), job);
    }
}
