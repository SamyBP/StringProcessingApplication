package com.app.restserver.services;

import com.app.restserver.dtos.ProcessingPipe;
import com.app.restserver.persistance.queue.QueueDispatcherStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueueDispatcherService {
    private final Map<Integer, QueueDispatcherStrategy> queueDispatcherStrategies;
    private final ObjectMapper objectMapper;

    @Autowired
    public QueueDispatcherService(Map<Integer, QueueDispatcherStrategy> queueDispatcherStrategies, ObjectMapper objectMapper) {
        this.queueDispatcherStrategies = queueDispatcherStrategies;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public void send(ProcessingPipe processingPipe) {
        String jobJson = objectMapper.writeValueAsString(processingPipe);
        QueueDispatcherStrategy queueDispatcher = queueDispatcherStrategies.get(processingPipe.getVersion());
        queueDispatcher.put(jobJson);
    }
}
