package com.app.restserver.endpoints.responses;

import lombok.Data;

@Data
public class CallbackResponse {
    private Long id;
    private String result;
    private Boolean isSuccess;
    private Long startedAt;
    private Long endedAt;
}
