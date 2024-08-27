package com.app.restserver;

import com.app.restserver.authentication.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtUtilTests {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void initialize() {
        jwtUtil = new JwtUtil();
        jwtUtil.setPrivateKey("dUc220DwnxIwYL0V8d3soryieuZMgX_7-Z_CNhFpCuk=");
        jwtUtil.setValidPeriod(10_000L);
    }

    @Test
    public void whenExtractClaimFromValidToken_returnsCorrectClaim() {
        String jwtToken = jwtUtil.generate(1L, "test");

        long userId = jwtUtil.extract("userId", Long.class, jwtToken);
        String username = jwtUtil.extract("username", String.class, jwtToken);

        assertEquals(1, userId);
        assertEquals("test", username);
    }

    @Test
    public void whenExtractClaimFromInvalidToken_throwsJwtException() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        assertThrows(JwtException.class, () -> jwtUtil.extract("name", String.class, token));
    }

    @Test
    public void whenExtractClaimFromExpiredToken_throwsJwtException() throws InterruptedException {
        jwtUtil.setValidPeriod(100L);
        String jwtToken = jwtUtil.generate(1L, "test");

        Thread.sleep(100L);

        assertThrows(JwtException.class, () -> {
            jwtUtil.extract("userId", Long.class, jwtToken);
            jwtUtil.extract("username", String.class, jwtToken);
        });
    }
}
