package com.app.restserver.services;

import com.app.restserver.authentication.PasswordEncoder;
import com.app.restserver.endpoints.requests.LoginRequest;
import com.app.restserver.endpoints.requests.RegisterRequest;
import com.app.restserver.entities.User;
import com.app.restserver.persistance.core.UserRepository;
import com.app.restserver.validations.authentication.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest) {
        String plainTextPassword = registerRequest.getPassword();
        String hashedPassword = passwordEncoder.encode(plainTextPassword);
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), hashedPassword);
        userRepository.save(user);
    }

    @Transactional
    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailIfExists(loginRequest.getEmail());
        checkPlainMatchesHashed(loginRequest.getPassword(), user.getPassword());
        userRepository.setLastLogin(user.getId(), LocalDateTime.now());
        return user;
    }

    private void checkPlainMatchesHashed(String plain, String hashed) {
        if (!passwordEncoder.matches(plain, hashed))
            throw new InvalidPasswordException("Wrong password");
    }
}
