package com.example.E_care.Alerte.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.example.E_care.Utilisateurs.Authentification.Config.JwtUtils;

import io.jsonwebtoken.JwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // Notez le changement de /user à /queue
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user"); // Important pour les messages privés
    }

    @Override
public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*") // En dev seulement, spécifiez les origines en prod
            .setHandshakeHandler(new DefaultHandshakeHandler() {
                @Override
                protected Principal determineUser(@NonNull ServerHttpRequest request, 
                                              @NonNull WebSocketHandler wsHandler,
                                              @NonNull Map<String, Object> attributes) {
                    // Extraire le token des paramètres de requête ou headers
                    String token = extractToken(request);
                    
                    if (token != null && jwtUtils.validateJwtToken(token)) {
                        String username = jwtUtils.getUsernameFromJwtToken(token);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        return new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    }
                    return null; // Rejettera la connexion si token invalide
                }
            })
            .withSockJS(); // Optionnel - pour fallback si besoin
}

private String extractToken(ServerHttpRequest request) {
    // 1. Vérifier dans les paramètres de requête
    if (request instanceof ServletServerHttpRequest) {
        String tokenParam = ((ServletServerHttpRequest) request).getServletRequest()
                            .getParameter("token");
        if (tokenParam != null) return tokenParam;
    }
    
    // 2. Vérifier dans les headers
    String authHeader = request.getHeaders().getFirst("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        return authHeader.substring(7);
    }
    
    return null;
}

    @Override
public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
        @Override
        public Message<?> preSend(@NonNull Message<?> message,@NonNull MessageChannel channel) {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                String token = accessor.getFirstNativeHeader("Authorization");
                try {
                    if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7);
                        if (!jwtUtils.validateJwtToken(token)) {
                            throw new JwtException("Invalid token");
                        }
                        String username = jwtUtils.getUsernameFromJwtToken(token);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        accessor.setUser(auth);
                    }
                } catch (JwtException e) {
                    // Log l'erreur et rejeter la connexion
                    logger.error("WebSocket JWT validation failed: " + e.getMessage());
                    return null; // Rejeter le message
                }
            }
            return message;
        }
    });
}
}
