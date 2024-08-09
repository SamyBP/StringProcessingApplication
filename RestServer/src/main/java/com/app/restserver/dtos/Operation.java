package com.app.restserver.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;

public class Operation {
    @NotNull private OperationType type;
    @NotNull private HashMap<String, Object> args;

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public HashMap<String, Object> getArgs() {
        return args;
    }

    public void setArgs(HashMap<String, Object> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", args=" + args +
                '}';
    }
}
