package com.app.restserver.persistance.core;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E> extends JpaRepository<E, Long> {
    default E findByIdIfExists(Long id) {
        return findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Entity with id:%d does not exist", id))
        );
    }
}
