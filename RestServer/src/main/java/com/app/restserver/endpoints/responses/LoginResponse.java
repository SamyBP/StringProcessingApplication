package com.app.restserver.endpoints.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private long expiresIn;
    private String token;

    public LoginResponse(String username, long expiresIn, String token) {
        this.username = username;
        this.expiresIn = expiresIn;
        this.token = token;
    }
}
