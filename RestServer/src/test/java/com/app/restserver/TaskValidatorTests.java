package com.app.restserver;

import com.app.restserver.validations.SubstringTaskValidator;
import com.app.restserver.validations.TaskValidator;
import com.app.restserver.validations.TrimTaskValidator;
import com.app.restserver.validations.UpperLowerTaskValidator;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskValidatorTests {

    @Test
    public void whenSubstringWithWrongArgsNames_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("wrong name", 3, "another wrong name", 4);

        TaskValidator taskValidator = new SubstringTaskValidator();
        boolean isValid = taskValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenSubstringWithWrongArgumentTypes_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("start", "1", "end", "wrong");

        TaskValidator taskValidator = new SubstringTaskValidator();
        boolean isValid = taskValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenSubstringWithStartMoreThanEnd_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("start", 4, "end", 2);

        TaskValidator taskValidator = new SubstringTaskValidator();
        boolean isValid = taskValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenSubstringWithStartOrEndLessThanZero_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("start", -2, "end", -4);

        TaskValidator taskValidator = new SubstringTaskValidator();
        boolean isValid = taskValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenUpperOrLowerWithAtLeastOneArgument_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("argument", "value");

        TaskValidator taskValidator = new UpperLowerTaskValidator();
        boolean isValid = taskValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenTrimWithWrongArgumentName_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("argument", "");

        TaskValidator taskValidator = new TrimTaskValidator();
        boolean isValid = taskValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenTrimWithArgumentValueNotCharacter_returnsFalse() {
        Map<String, Object> givenArgs = Map.of("character", 12);

        TaskValidator taskValidator = new TrimTaskValidator();
        boolean isValid = taskValidator.isValid(givenArgs);

        assertFalse(isValid);
    }

    @Test
    public void whenValidArguments_returnsTrue() {
        TaskValidator taskValidator;

        taskValidator = new SubstringTaskValidator();
        assertTrue(taskValidator.isValid(Map.of("start", 1, "end", 4)));

        taskValidator = new UpperLowerTaskValidator();
        assertTrue(taskValidator.isValid(Map.of()));

        taskValidator = new TrimTaskValidator();
        assertTrue(taskValidator.isValid(Map.of("character", "2")));
    }
}
