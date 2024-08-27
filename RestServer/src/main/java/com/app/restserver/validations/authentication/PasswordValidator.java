package com.app.restserver.validations.authentication;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() < 8)
            throw new InvalidPasswordException("Password must contain at least 8 characters");

        boolean hasUpper = false, hasLower = false, hasDigit = false;

        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpper = true;
            if (Character.isLowerCase(c))
                hasLower = true;
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        if (!hasUpper)
            throw new InvalidPasswordException("Password must contain at least one uppercase character");
        if (!hasLower)
            throw new InvalidPasswordException("Password must contain at least one lowercase character");
        if (!hasDigit)
            throw new InvalidPasswordException("Password must contain at least one digit");

        return true;
    }
}
