package com.app.restserver.validations;

import java.util.Map;

public class TrimTaskValidator implements TaskValidator{
    @Override
    public boolean isValid(Map<String, Object> args) {
        if (args.size() != 1)
            return false;

        Object character = args.get("character");

        return character instanceof String && ((String) character).length() == 1;
    }
}
