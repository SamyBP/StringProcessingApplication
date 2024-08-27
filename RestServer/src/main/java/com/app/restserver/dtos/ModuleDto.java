package com.app.restserver.dtos;

import com.app.restserver.validations.module.SupportedModule;
import lombok.*;

import java.util.Map;

@Builder
@Getter @Setter @ToString
@SupportedModule
public class ModuleDto {
    private ModuleName name;
    private Map<String, Object> args;
}
