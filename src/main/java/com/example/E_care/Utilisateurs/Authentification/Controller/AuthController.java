package com.example.E_care.Utilisateurs.Authentification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.E_care.Utilisateurs.models.User;
import org.springframework.http.ResponseEntity;

import com.example.E_care.Utilisateurs.Authentification.Config.JwtUtils;
import com.example.E_care.Utilisateurs.Authentification.Dao.UserDao;
import com.example.E_care.Utilisateurs.models.Administrateur;
import com.example.E_care.Utilisateurs.models.Apprenant;
import com.example.E_care.Utilisateurs.models.LoginDto;
import com.example.E_care.Utilisateurs.models.RegisterDto;
import com.example.E_care.Utilisateurs.models.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDao userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(jwt);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erreur: Nom d'utilisateur ou mot de passe incorrect !");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("Erreur: Nom d'utilisateur déjà pris !");
        }

        User user;

        if (registerDto.getRole().equals(Role.ADMIN)) {
            Administrateur admin = new Administrateur();
            admin.setHopital(registerDto.getHopital());
            user = admin;
        } else if (registerDto.getRole().equals(Role.APPRENANT)) {
            Apprenant apprenant = new Apprenant();
            apprenant.setLocalisation(registerDto.getLocalisation());
            apprenant.setSexe(registerDto.getSexe());
            apprenant.setDate_naissance(registerDto.getDate_naissance());
            apprenant.setDivers(registerDto.getDivers());
            user = apprenant;
        } else {
            return ResponseEntity.badRequest().body("Erreur: Rôle inconnu !");
        }
        user.setUsername(registerDto.getUsername());
        user.setNom(registerDto.getNom());
        user.setPrenom(registerDto.getPrenom());
        user.setTelephone(registerDto.getTelephone());
        user.setUrl(registerDto.getProfil());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(registerDto.getRole());

        userRepository.save(user);
        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }
}

