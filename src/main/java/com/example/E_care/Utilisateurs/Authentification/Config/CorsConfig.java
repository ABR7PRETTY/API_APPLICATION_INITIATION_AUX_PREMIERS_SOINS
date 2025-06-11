package com.example.E_care.Utilisateurs.Authentification.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

@Configuration
public class CorsConfig {
    
    @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    
    config.setAllowedOrigins(List.of(
        "http://localhost:4200",                    // Angular local
        "http://10.0.2.2:4200",                     // Android Emulator (localhost redirection)
        "capacitor://localhost",                   // Capacitor mobile app
        "http://localhost",                        // Pour d'autres cas de test
        "https://ecare-web-sooty.vercel.app"       // Site déployé
    ));
    
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}

}
