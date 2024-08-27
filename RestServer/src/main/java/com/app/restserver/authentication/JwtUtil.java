package com.app.restserver.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Getter @Setter
public class JwtUtil {
    @Value("${auth.jwt.private.key}")
    private String privateKey;
    @Value("#{T(java.lang.Long).parseLong('${auth.jwt.valid.period}')}")
    private Long validPeriod;
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String generate(Long userId, String username) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validPeriod))
                .signWith(getVerificationKey())
                .compact();
    }

    public <T> T extract(String claim, Class<T> type, String token) {
        return Jwts.parser()
                .verifyWith(getVerificationKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(claim, type);
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(getVerificationKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    private SecretKey getVerificationKey() {
        return Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
    }
}
