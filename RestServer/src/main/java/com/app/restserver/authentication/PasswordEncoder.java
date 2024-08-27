package com.app.restserver.authentication;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encode(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean matches(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
