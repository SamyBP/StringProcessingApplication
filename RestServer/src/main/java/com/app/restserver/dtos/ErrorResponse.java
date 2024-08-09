package com.app.restserver.dtos;

public record ErrorResponse(
        String path,
        int status,
        String message
) {
}
