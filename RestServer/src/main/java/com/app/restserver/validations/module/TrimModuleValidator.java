package com.app.restserver.validations.module;

import java.util.Map;

public class TrimModuleValidator implements ModuleValidator {
    @Override
    public boolean isValid(Map<String, Object> args) {
        if (args.size() != 1 || !args.containsKey("character"))
            throw new InvalidModuleException("Module TRIM supports 'character' as parameter");

        Object character = args.get("character");

        if (!(character instanceof String) || ((String) character).length() != 1)
            throw new InvalidModuleException("Module TRIM must have a char value for it's parameter");

        return true;
    }
}
