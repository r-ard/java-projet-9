package com.medilabo.gateway.service;

import com.medilabo.gateway.model.User;
import com.medilabo.gateway.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<User> userPromise = userRepository.findByUsername(username);

        return userPromise.map(user -> {
            log.info("Found user to authenticate : " + username);

            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .build();
        }).switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with following username : '" + username + "'")));
    }
}
