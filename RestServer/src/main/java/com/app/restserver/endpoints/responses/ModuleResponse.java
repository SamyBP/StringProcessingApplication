package com.app.restserver.endpoints.responses;

import com.app.restserver.entities.Module;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class ModuleResponse {
    private Long id;
    private String name;
    @JsonRawValue
    private String args;

    public static ModuleResponse fromEntity(Module module) {
        return ModuleResponse.builder()
                .id(module.getId())
                .name(module.getName())
                .args(module.getArgs())
                .build();
    }
}
