package com.app.restserver.authentication;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationTokenResolver implements HandlerMethodArgumentResolver {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationTokenResolver.class);
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationTokenResolver(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(FromToken.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        FromToken fromToken = parameter.getParameterAnnotation(FromToken.class);
        assert fromToken != null;
        logger.info(String.format("Trying to inject claim:%s into parameter of type: %s", fromToken.extract(), parameter.getParameterType()));
        return jwtUtil.extract(fromToken.extract(), parameter.getParameterType(), request.getHeader("token"));
    }
}
