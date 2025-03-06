package com.proacademy.proacademy.configs;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

    private static final String[] PUBLIC_MATCHERS = {
        "/"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
        "/user",
        "/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .csrf(csrf -> csrf.disable());
            http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated());
        
        http
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configration = new CorsConfiguration().applyPermitDefaultValues();
        configration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}