package com.app.restserver.endpoints.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorResponse {
    String path;
    int status;
    String message;
}
