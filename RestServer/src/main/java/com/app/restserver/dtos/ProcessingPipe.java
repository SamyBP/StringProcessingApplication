package com.app.restserver.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Builder
@Getter @Setter @ToString
public class ProcessingPipe {
    @NotNull
    private Long id;
    @NotEmpty
    private String input;
    @URL
    private String callback;
    @Range(min = 1, max = 2, message = "must be 1 or 2")
    private int version;
    @NotNull @Valid
    private List<ModuleDto> modules;
}
