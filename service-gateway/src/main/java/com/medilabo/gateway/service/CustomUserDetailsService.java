package com.medilabo.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Component
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    @Value("${auth.username}")
    private String internalUsername;

    @Value("${auth.password}")
    private String internalPassword;

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Try to found user : " + username);

        if(!internalUsername.equals(username)) {
            log.error("User not found with following username : '" + username + "'");

            return Mono.empty();
        }

        return Mono.just(
                org.springframework.security.core.userdetails.User.withUsername(username)
                .password(passwordEncoder.encode(internalPassword))
                .build()
        );
    }
}
