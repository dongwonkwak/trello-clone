package com.trelloclone.backend.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class DefaultSecurityConfig {

    @Value("${application.frontend-url}")
    private String frontendUrl;

    private static final RequestMatcher[] PUBLIC_URLS = new RequestMatcher[] {
            // 회원 가입,
            new AntPathRequestMatcher("/auth/signup", "POST"),
            // 계정 활성화 링크
            new AntPathRequestMatcher("/activate", "GET"),
            // 계정 활성화 재전송
            new AntPathRequestMatcher("/resend-activation"),
            // 계정 활성화 재전송 성공
            new AntPathRequestMatcher("/resend-activation-success", "GET")
    };

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated())
                .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        var cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.setAllowedOrigins(List.of(frontendUrl));
        cors.addAllowedOrigin(frontendUrl);
        cors.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
