package com.app.restserver.validations.module;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SupportedModuleValidator.class)
public @interface SupportedModule {
    String message() default "Operation not supported";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
