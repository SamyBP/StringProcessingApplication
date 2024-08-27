package com.app.restserver.validations.module;

import java.util.Map;

public class SubstringModuleValidator implements ModuleValidator {
    @Override
    public boolean isValid(Map<String, Object> args) {
        if (args.size() != 2 || !args.containsKey("start") || !args.containsKey("end"))
            throw new InvalidModuleException("Module SUBSTRING supports 'start' and 'end' as parameters");

        Object start = args.get("start");
        Object end = args.get("end");

        if (!(start instanceof Integer) || !(end instanceof Integer))
            throw new InvalidModuleException("Module SUBSTRING must have integer values for it's parameters");

        if ((int) start < 0 || (int) end < 0)
            throw new InvalidModuleException("Module SUBSTRING cannot have negative values for it's parameters");

        if ((int) start > (int) end)
            throw new InvalidModuleException("Module SUBSTRING should not have 'start' value greater than 'end' value");

        return true;
    }
}
