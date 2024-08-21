package com.app.restserver.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresJobRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresJobRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void putToQueue(String job) {
        jdbcTemplate.update(
                "insert into mq.queue(message) values (?)",
                job
        );
    }
}
