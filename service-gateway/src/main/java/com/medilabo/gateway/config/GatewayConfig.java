package com.medilabo.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Value("${gateway.service.patient.uri}")
    private String patientServiceUri;

    @Value("${gateway.service.note.uri}")
    private String noteServiceUri;

    @Value("${gateway.service.diabetrisk.uri}")
    private String diabetRiskServiceUri;

    @Value("${gateway.service.front.uri}")
    private String frontServiceUri;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeBuilder) {
        return routeBuilder.routes()
                .route("service-patient", (r) -> r.path("/patients/**")
                                .uri(patientServiceUri)
                )
                .route("service-note", (r) -> r.path("/notes/**")
                        .uri(noteServiceUri)
                )
                .route("service-diabetrisk", (r) -> r.path("/diabet-risk/**")
                        .uri(diabetRiskServiceUri)
                )
                .route("service-front", (r) -> r.path("/front/**")
                        .uri(frontServiceUri)
                )
                .build();

    }
}
