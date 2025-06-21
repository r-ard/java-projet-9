package com.medilabo.gateway.config;

import com.medilabo.gateway.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import reactor.core.publisher.Mono;

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

    /**
     * Règles :
     *
     * - Désactivation de la protection CSRF (afin de faciliter les appels API et l'authentification).
     *
     * - Autorisation d’accès public à certaines ressources :
     *     - /service-front/login : page de connexion et route de connexion post
     *     - /service-front/style.css : style css
     *
     * - Tous les autres endpoints nécessitent une authentification.
     *
     * - Configuration de l’authentification :
     *     - En cas de succès, l’utilisateur est redirigé vers /service-front/patients.
     *     - En cas d’échec, l’utilisateur est redirigé vers la page de connexion.
     *     - Les connexions réussies sont enregistrées dans les logs avec le nom de l’utilisateur.
     *     - Les échecs de connexion sont également enregistrés.
     *
     * - Configuration de la déconnexion :
     *     - L’URL de déconnexion est /logout.
     *     - Après la déconnexion, l’utilisateur est redirigé vers la page de connexion.
     *
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec -> exchangeSpec
                        .pathMatchers("/service-front/login", "/service-front/style.css").permitAll()
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