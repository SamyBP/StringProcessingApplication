package com.app.restserver.endpoints.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class PipeUpdateRequest {
    private Long pipeId;
    private Boolean isPublic;
    @NotEmpty
    private List<Long> moduleIds;
}
