package com.app.restserver.configuration;

import com.app.restserver.authentication.AuthenticationTokenInterceptor;
import com.app.restserver.authentication.AuthenticationTokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebAuthConfiguration implements WebMvcConfigurer {
    private final AuthenticationTokenInterceptor authenticationTokenInterceptor;
    private final AuthenticationTokenResolver authenticationTokenResolver;

    @Autowired
    public WebAuthConfiguration(AuthenticationTokenInterceptor authenticationTokenInterceptor, AuthenticationTokenResolver authenticationTokenResolver) {
        this.authenticationTokenInterceptor = authenticationTokenInterceptor;
        this.authenticationTokenResolver = authenticationTokenResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationTokenInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationTokenResolver);
    }
}
