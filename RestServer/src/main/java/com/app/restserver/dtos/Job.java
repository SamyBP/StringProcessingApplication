package com.app.restserver.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Builder
@Getter
@Setter
public class Job {
    @NotBlank
    private String id;
    @NotEmpty
    private String input;
    @URL
    private String callback;
    @NotNull @Valid
    private List<Task> tasks;

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", input='" + input + '\'' +
                ", callback='" + callback + '\'' +
                ", operations=" + tasks +
                '}';
    }
}
