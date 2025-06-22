package com.medilabo.front.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class FeignClientConfig {
    @Value("${gateway.auth.username}")
    private String username;

    @Value("${gateway.auth.password}")
    private String password;

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String auth = username + ":" + password;
                String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                template.header("Authorization", "Basic " + encodedAuth);
            }
        };
    }
}
