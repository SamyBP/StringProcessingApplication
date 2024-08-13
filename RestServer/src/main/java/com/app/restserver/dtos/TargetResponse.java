package com.app.restserver.dtos;

import lombok.Data;

@Data
public class TargetResponse {
    private String id;
    private String result;
    private String is_success;
}
