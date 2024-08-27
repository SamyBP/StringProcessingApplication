package com.app.restserver.validations.module;

public class InvalidModuleException extends RuntimeException{
    public InvalidModuleException(String message) {
        super(message);
    }
}
