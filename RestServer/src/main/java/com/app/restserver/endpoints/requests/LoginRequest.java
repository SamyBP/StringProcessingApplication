package com.app.restserver.endpoints.requests;

import com.app.restserver.validations.authentication.Password;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    private String email;
    @Password
    private String password;
}
