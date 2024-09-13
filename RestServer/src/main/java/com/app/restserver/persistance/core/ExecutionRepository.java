package com.app.restserver.persistance.core;

import com.app.restserver.entities.Execution;

import java.util.List;

public interface ExecutionRepository extends BaseRepository<Execution> {
    List<Execution> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
