package com.malgn.configure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageableConfiguration {

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer() {
        return resolver -> {
            resolver.setFallbackPageable(PageRequest.of(0, 10));
            resolver.setMaxPageSize(30);
        };
    }
}
