package com.app.restserver.persistance.core;

import com.app.restserver.entities.Module;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ModuleRepository extends BaseRepository<Module> {
    @Query(nativeQuery = true, value = """
            select m.* from module m
            join pipe_module pm on m.id = pm.module_id and pm.pipe_id = :pipeId
            order by pm.id
    """)
    List<Module> findPipeDefinitionModules(@Param("pipeId") Long pipeId);

    default List<Module> findAllModulesOrderedByDefinitionId(List<Long> definitionIds) {
        List<Module> modules = new ArrayList<>();

        for (Long moduleId : definitionIds) {
            modules.add(getReferenceById(moduleId));
        }

        return modules;
    }
}
