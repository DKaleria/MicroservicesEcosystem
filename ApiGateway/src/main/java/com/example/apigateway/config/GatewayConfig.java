package com.example.apigateway.config;

import com.example.apigateway.filter.JwtValidationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtValidationFilter jwtValidationFilter) {
        return builder.routes()
                .route("identity-service", r -> r.path("/identity/authenticate")
                        .filters(f -> f.filter(jwtValidationFilter))
                        .uri("http://localhost:8082/identity-service/identity/authenticate"))
                .route("identity-service", r -> r.path("/identity/register")
                        .uri("http://localhost:8082/identity-service"))
                .route("identity-service", r -> r.path("/identity/users")
                        .filters(f -> f.filter(jwtValidationFilter))
                        .uri("http://localhost:8082/identity-service"))
                .route("product-service", r -> r.path("/products/**")
                        .filters(f -> f.filter(jwtValidationFilter))
                        .uri("http://localhost:8082/product-service"))
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(jwtValidationFilter))
                        .uri("http://localhost:8082/user-service"))
                .build();
    }
}