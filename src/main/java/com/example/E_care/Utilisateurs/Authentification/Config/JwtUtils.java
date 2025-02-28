package com.example.E_care.Utilisateurs.Authentification.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtUtils {
    // Génération automatique d'une clé secrète sécurisée
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final int jwtExpirationMs = 86400000; // 24 heures

    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

     public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
