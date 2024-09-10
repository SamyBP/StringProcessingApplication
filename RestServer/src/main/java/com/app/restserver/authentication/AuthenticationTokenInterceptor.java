package com.app.restserver.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationTokenInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationTokenInterceptor.class);
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationTokenInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;

        Authenticated authenticated = ((HandlerMethod) handler).getMethodAnnotation(Authenticated.class);

        if (authenticated == null)
            return true;

        logger.info("Trying to validate the token");
        String token = request.getHeader("token");
        if (token == null || !jwtUtil.isValidToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
