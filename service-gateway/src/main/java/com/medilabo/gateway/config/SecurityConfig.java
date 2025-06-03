package com.medilabo.gateway.config;

import com.medilabo.gateway.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import java.net.URI;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    public SecurityConfig(CustomUserDetailsService userDetailsService) {

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec -> exchangeSpec
                        .pathMatchers("/service-front/login", "/service-front/css/**", "/service-front/images/**", "/service-front/js/**").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/service-front/login")
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            log.info("Login success for : {}", authentication.getName());
                            return new RedirectServerAuthenticationSuccessHandler("/service-front/patients")
                                    .onAuthenticationSuccess(webFilterExchange, authentication);
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            log.info("Login failed");
                            return new RedirectServerAuthenticationFailureHandler("/service-front/login")
                                    .onAuthenticationFailure(webFilterExchange, exception);
                        })
                )
                .logout(logout -> {
                    RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
                    logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/service-front/login"));
                    logout.logoutUrl("/logout")
                            .logoutSuccessHandler(logoutSuccessHandler);
                });

        return http.build();
    }
}