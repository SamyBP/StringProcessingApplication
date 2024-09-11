package com.app.restserver.validations.module;

import java.util.Map;

public class SubstringModuleValidator implements ModuleValidator {
    @Override
    public boolean isValid(Map<String, Object> args) {
        if (args.size() != 2 || !args.containsKey("start") || !args.containsKey("end"))
            throw new InvalidModuleException("Module SUBSTRING supports 'start' and 'end' as parameters");

        int start = getValueForArgument(args.get("start"));
        int end = getValueForArgument(args.get("end"));

        if (start < 0 || end < 0)
            throw new InvalidModuleException("Module SUBSTRING cannot have negative values for it's parameters");

        if (start >  end)
            throw new InvalidModuleException("Module SUBSTRING should not have 'start' value greater than 'end' value");

        return true;
    }

    private int getValueForArgument(Object argument) {
        if (argument instanceof String) {
            return Integer.parseInt(argument.toString());
        }

        if (argument instanceof Integer) {
            return (int) argument;
        }

        throw new InvalidModuleException("Module SUBSTRING must have integer values for it's parameters");
    }
}
