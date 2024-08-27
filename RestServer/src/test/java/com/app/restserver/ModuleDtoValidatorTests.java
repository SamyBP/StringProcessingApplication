package com.app.restserver;

import com.app.restserver.validations.module.SubstringModuleValidator;
import com.app.restserver.validations.module.ModuleValidator;
import com.app.restserver.validations.module.TrimModuleValidator;
import com.app.restserver.validations.module.UpperLowerModuleValidator;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleDtoValidatorTests {

    @Test
    public void whenSubstringWithWrongArgsNames_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("wrong name", 3, "another wrong name", 4);

        ModuleValidator moduleValidator = new SubstringModuleValidator();
        boolean isValid = moduleValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenSubstringWithWrongArgumentTypes_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("start", "1", "end", "wrong");

        ModuleValidator moduleValidator = new SubstringModuleValidator();
        boolean isValid = moduleValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenSubstringWithStartMoreThanEnd_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("start", 4, "end", 2);

        ModuleValidator moduleValidator = new SubstringModuleValidator();
        boolean isValid = moduleValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenSubstringWithStartOrEndLessThanZero_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("start", -2, "end", -4);

        ModuleValidator moduleValidator = new SubstringModuleValidator();
        boolean isValid = moduleValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenUpperOrLowerWithAtLeastOneArgument_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("argument", "value");

        ModuleValidator moduleValidator = new UpperLowerModuleValidator();
        boolean isValid = moduleValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenTrimWithWrongArgumentName_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("argument", "");

        ModuleValidator moduleValidator = new TrimModuleValidator();
        boolean isValid = moduleValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenTrimWithArgumentValueNotCharacter_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("character", 12);

        ModuleValidator moduleValidator = new TrimModuleValidator();
        boolean isValid = moduleValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenValidArguments_returnsTrue() {
        ModuleValidator moduleValidator;

        moduleValidator = new SubstringModuleValidator();
        assertTrue(moduleValidator.isValid(Map.of("start", 1, "end", 4)));

        moduleValidator = new UpperLowerModuleValidator();
        assertTrue(moduleValidator.isValid(Map.of()));

        moduleValidator = new TrimModuleValidator();
        assertTrue(moduleValidator.isValid(Map.of("character", "2")));
    }
}
