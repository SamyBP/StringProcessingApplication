package com.app.restserver.endpoints.requests;

import com.app.restserver.validations.authentication.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull
    private String username;
    @Email
    private String email;
    @Password
    private String password;

}
