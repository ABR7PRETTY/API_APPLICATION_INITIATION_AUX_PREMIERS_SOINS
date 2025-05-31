package com.example.E_care.Utilisateurs.Authentification.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    
    @Bean
public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("*"); // Permet toutes les origines
    config.addAllowedHeader("*"); // Autorise tous les en-têtes
    config.addAllowedMethod("*"); // Autorise GET, POST, PUT, DELETE...
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
}
}
