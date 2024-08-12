package com.app.restserver.validations;

import java.util.Map;

public class UpperLowerTaskValidator implements TaskValidator{
    @Override
    public boolean isValid(Map<String, Object> args) {
        return args.isEmpty();
    }
}
