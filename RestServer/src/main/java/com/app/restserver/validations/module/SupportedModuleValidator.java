package com.app.restserver.validations.module;

import com.app.restserver.dtos.ModuleDto;
import com.app.restserver.dtos.ModuleName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public class SupportedModuleValidator implements ConstraintValidator<SupportedModule, ModuleDto> {
    private final Map<ModuleName, ModuleValidator> moduleValidators;

    @Autowired
    public SupportedModuleValidator(Map<ModuleName, ModuleValidator> moduleValidators) {
        this.moduleValidators = moduleValidators;
    }

    @Override
    public void initialize(SupportedModule constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ModuleDto module, ConstraintValidatorContext constraintValidatorContext) {
        if (module.getName() == null || module.getArgs() == null)
            return false;
        ModuleValidator moduleValidator = moduleValidators.get(module.getName());
        return moduleValidator.isValid(module.getArgs());
    }
}
