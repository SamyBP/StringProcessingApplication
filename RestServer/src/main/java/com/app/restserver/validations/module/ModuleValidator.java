package com.app.restserver.validations.module;

import java.util.Map;

public interface ModuleValidator {
    boolean isValid(Map<String, Object> args);
}
