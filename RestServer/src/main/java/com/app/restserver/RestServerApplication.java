package com.app.restserver;


import com.app.restserver.dtos.TaskType;
import com.app.restserver.validations.SubstringTaskValidator;
import com.app.restserver.validations.TaskValidator;
import com.app.restserver.validations.TrimTaskValidator;
import com.app.restserver.validations.UpperLowerTaskValidator;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class RestServerApplication {
    @Value("${rabbitmq.work.queue}")
    private String workQueueName;

    @Bean
    public Queue messageQueue() {
        return new Queue(workQueueName, true);
    }

    @Bean
    public Map<TaskType, TaskValidator> taskValidators() {
        Map<TaskType, TaskValidator> validators = new HashMap<>();

        validators.put(TaskType.SUBSTRING, new SubstringTaskValidator());
        validators.put(TaskType.LOWER, new UpperLowerTaskValidator());
        validators.put(TaskType.UPPER, new UpperLowerTaskValidator());
        validators.put(TaskType.TRIM, new TrimTaskValidator());

        return validators;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestServerApplication.class, args);
    }
}
