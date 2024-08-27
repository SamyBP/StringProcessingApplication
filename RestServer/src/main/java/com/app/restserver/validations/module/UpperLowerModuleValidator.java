package com.app.restserver.validations.module;

import java.util.Map;

public class UpperLowerModuleValidator implements ModuleValidator {
    @Override
    public boolean isValid(Map<String, Object> args) {
        if (!args.isEmpty())
            throw new InvalidModuleException("Modules UPPER and LOWER do not accept arguments");
        return true;
    }
}
