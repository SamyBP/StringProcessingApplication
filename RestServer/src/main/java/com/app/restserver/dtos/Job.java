package com.app.restserver.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public class Job {
    @NotBlank private String id;
    @NotEmpty private String input;
    @URL private String callback;
    @NotNull @Valid private List<Operation> operations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", input='" + input + '\'' +
                ", callback='" + callback + '\'' +
                ", operations=" + operations +
                '}';
    }
}
