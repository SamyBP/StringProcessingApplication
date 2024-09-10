package com.app.restserver.endpoints.responses;

import com.app.restserver.entities.Pipe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter
public class PipeResponse {
    private Long id;
    private String ownerUsername;
    private String name;
    private LocalDateTime createdAt;
    private Boolean isPublic;
    private List<ModuleResponse> modules;

    public static PipeResponse fromEntity(Pipe pipe) {
        return PipeResponse.builder()
                .id(pipe.getId())
                .ownerUsername(pipe.getUser().getUsername())
                .name(pipe.getName())
                .createdAt(pipe.getCreatedAt())
                .isPublic(pipe.isPublic())
                .modules(pipe.getModules().stream().map(ModuleResponse::fromEntity).toList())
                .build();
    }
}
