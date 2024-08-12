package com.app.restserver.validations;

import java.util.Map;

public class SubstringTaskValidator implements TaskValidator{
    @Override
    public boolean isValid(Map<String, Object> args) {
        if (args.size() != 2 || !args.containsKey("start") || !args.containsKey("end"))
            return false;

        Object start = args.get("start");
        Object end = args.get("end");

        if (!(start instanceof Integer) || !(end instanceof Integer))
            return false;

        return (int) start >= 0 && (int) end >= 0 && (int) start <= (int) end;
    }
}
