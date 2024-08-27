package com.app.restserver.persistance.core;

import com.app.restserver.entities.Pipe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface PipeRepository extends BaseRepository<Pipe> {
    @Query(nativeQuery = true, value = """
        select p.* from pipe p where p.user_id = :userId or p.is_public = true
    """)
    List<Pipe> findAllCreatedByUserOrPublicPipes(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = """
        select user_id from pipe where id = :pipeId
    """)
    Long findOwnerIdForPipe(@Param("pipeId") Long pipeId);

    @Transactional
    default void updatePipeDefinition(Long pipeId, boolean isPublic,List<Long> moduleIds) {
        removePipeDefinition(pipeId);
        updatePipeVisibility(pipeId, isPublic);
        moduleIds.forEach(moduleId -> addModuleToPipeDefinition(pipeId, moduleId));
    }

    @Modifying
    @Query(nativeQuery = true, value = """
        delete from pipe_module where pipe_id = :pipeId
    """)
    void removePipeDefinition(@Param("pipeId") Long pipeId);

    @Modifying
    @Query(nativeQuery = true, value = """
        update pipe set is_public = :isPublic where id = :pipeId
    """)
    void updatePipeVisibility(@Param("pipeId") Long pipeId,@Param("isPublic") boolean isPublic);

    @Modifying
    @Query(nativeQuery = true, value = """
        insert into pipe_module(pipe_id, module_id) VALUES (:pipeId, :moduleId)
    """)
    void addModuleToPipeDefinition(@Param("pipeId") Long pipeId, @Param("moduleId") Long moduleId);
}
