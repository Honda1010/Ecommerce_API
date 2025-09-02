package com.EjadaFinalProject.WalletMicroServices.Authuntication.Config;

import com.EjadaFinalProject.WalletMicroServices.Authuntication.Filters.JwtAuthunticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
     private final JwtAuthunticationFilter jwtAuthunticationFilter;
     private final AuthenticationProvider authenticationProvider;
    public SecurityConfiguration(JwtAuthunticationFilter jwtAuthunticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthunticationFilter = jwtAuthunticationFilter;
        this.authenticationProvider = authenticationProvider;
    }
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/wallets/**").permitAll()
                        .requestMatchers("/transactions/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthunticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
