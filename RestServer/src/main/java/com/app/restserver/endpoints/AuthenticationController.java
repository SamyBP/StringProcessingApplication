package com.app.restserver.endpoints;

import com.app.restserver.authentication.JwtUtil;
import com.app.restserver.endpoints.requests.LoginRequest;
import com.app.restserver.endpoints.responses.LoginResponse;
import com.app.restserver.endpoints.requests.RegisterRequest;
import com.app.restserver.endpoints.util.EndpointsUtil;
import com.app.restserver.entities.User;
import com.app.restserver.services.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = EndpointsUtil.AUTHENTICATION)
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        logger.info(String.format("Received registration request: %s", registerRequest));
        authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        logger.info(String.format("Received login request: %s", loginRequest));
        User user = authenticationService.login(loginRequest);
        String token = jwtUtil.generate(user.getId(), user.getUsername());
        LoginResponse loginResponse = new LoginResponse(user.getUsername(), jwtUtil.getValidPeriod(), token);
        return ResponseEntity.ok(loginResponse);
    }
}
