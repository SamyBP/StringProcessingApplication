package com.app.restserver.validations;

import com.app.restserver.dtos.Task;
import com.app.restserver.dtos.TaskType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public class SupportedTaskValidator implements ConstraintValidator<SupportedTask, Task> {
    private final Map<TaskType, TaskValidator> taskValidators;

    @Autowired
    public SupportedTaskValidator(Map<TaskType, TaskValidator> taskValidators) {
        this.taskValidators = taskValidators;
    }

    @Override
    public void initialize(SupportedTask constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext constraintValidatorContext) {
        if (task.getType() == null || task.getArgs() == null)
            return false;
        TaskValidator taskValidator = taskValidators.get(task.getType());
        return taskValidator.isValid(task.getArgs());
    }
}
