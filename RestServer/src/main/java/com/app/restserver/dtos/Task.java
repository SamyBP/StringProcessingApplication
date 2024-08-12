package com.app.restserver.dtos;

import com.app.restserver.validations.SupportedTask;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
@SupportedTask
public class Task {
    private TaskType type;
    private Map<String, Object> args;

    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", args=" + args +
                '}';
    }
}
