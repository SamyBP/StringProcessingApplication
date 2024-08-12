package com.app.restserver.validations;

import java.util.Map;

public interface TaskValidator {
    boolean isValid(Map<String, Object> args);
}
