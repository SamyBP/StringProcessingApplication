package com.app.restserver.endpoints.requests;

import com.app.restserver.dtos.ModuleDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class PipeExecutionRequest {
    @NotEmpty
    private String input;
    @NotNull
    private Long pipeId;
    @Range(min = 1, max = 2, message = "must be 1 or 2")
    private int version;
    @NotNull @Valid
    private List<ModuleDto> modules;
}
