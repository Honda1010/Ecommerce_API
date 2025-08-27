package com.EjadaFinalProject.API_Gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/wallets/**", "/transactions/**", "/auth/**")
                        .uri("lb://WALLETMICROSERVICES"))
                .route(p -> p
                        .path("/inventory/**")
                        .uri("lb://INVENTORYMICROSERVICE"))
                .route(p -> p
                        .path("/orders/**", "/payments/**", "/cart/**","/products/**")
                        .uri("lb://SHOPMICROSERVICE"))
                .build();
    }


}
