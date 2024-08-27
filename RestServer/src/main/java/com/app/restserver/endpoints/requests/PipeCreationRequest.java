package com.app.restserver.endpoints.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class PipeCreationRequest {
    @NotBlank
    private String name;
    private Boolean isPublic;
    @NotEmpty
    private List<Long> moduleIds;
}
