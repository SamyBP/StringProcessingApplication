package com.app.restserver.endpoints.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private long expiresIn;
    private String token;

    public LoginResponse(long expiresIn, String token) {
        this.expiresIn = expiresIn;
        this.token = token;
    }
}
