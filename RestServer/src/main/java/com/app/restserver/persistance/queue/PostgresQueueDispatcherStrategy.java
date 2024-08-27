package com.app.restserver.persistance.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostgresQueueDispatcherStrategy implements QueueDispatcherStrategy {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresQueueDispatcherStrategy(@Qualifier("queueJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void put(String job) {
        jdbcTemplate.update(
                "insert into mq.queue(message) values (?)",
                job
        );
    }
}
