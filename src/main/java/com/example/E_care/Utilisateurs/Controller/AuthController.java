package com.example.E_care.Utilisateurs.Controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.E_care.Utilisateurs.models.User;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.E_care.Urgence.dao.HopitalDao;
import com.example.E_care.Urgence.models.Hopital;
import com.example.E_care.Utilisateurs.Authentification.Config.JwtUtils;
import com.example.E_care.Utilisateurs.dao.UserDao;
import com.example.E_care.Utilisateurs.models.Administrateur;
import com.example.E_care.Utilisateurs.models.SuperAdmin;
import com.example.E_care.Utilisateurs.models.Apprenant;
import com.example.E_care.Utilisateurs.models.LoginDto;
import com.example.E_care.Utilisateurs.models.Role;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
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
    @Autowired
    private HopitalDao hopitalDao;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            if (!user.isStatut()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Compte inactif. Contactez l'administrateur."));
            }
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("role", user.getRole().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            e.printStackTrace();
            errorResponse.put("error", "Erreur: Nom d'utilisateur ou mot de passe incorrect !");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam(name = "profil", required = false) MultipartFile profil
            // @RequestParam("hopitalId") Long hopitalId
    ) {
        SuperAdmin admin = new SuperAdmin();
        // Administrateur admin = new Administrateur();
      

        //Optional<Hopital> hopitalOpt = hopitalDao.findById(hopitalId);
        //if (hopitalOpt.isEmpty()) {
        //throw new RuntimeException("Hopital introuvable");
        //}

        // admin.setHopital(hopitalOpt.get());
        try {
            admin.setUsername(username);
            admin.setNom(nom);
            admin.setPrenom(prenom);
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(Role.SUPERADMIN);
            if (profil != null && !profil.isEmpty()) {
                admin.setProfil(profil.getBytes());
            } else {
                admin.setProfil(null); // ou une valeur par défaut
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        userRepository.save(admin);
        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }

    @PostMapping(value = "/registerApprenant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerApprenant(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("telephone") String telephone,
            @RequestParam(name ="profil", required = false) MultipartFile profil,
            @RequestParam("localisation") String localisation,
            @RequestParam("sexe") String sexe,
            @RequestParam("date_naissance") String date_naissance,
            @RequestParam("divers") String divers) {
        Apprenant apprenant = new Apprenant();

        try {
            apprenant.setUsername(username);
            apprenant.setNom(nom);
            apprenant.setPrenom(prenom);
            apprenant.setTelephone(telephone);
            apprenant.setDivers(divers);
            apprenant.setDate_naissance(date_naissance);
            apprenant.setSexe(sexe);
            apprenant.setLocalisation(localisation);
            if (profil != null && !profil.isEmpty()) {
                apprenant.setProfil(profil.getBytes());
            } else {
                apprenant.setProfil(null); // ou une valeur par défaut
            }
            apprenant.setPassword(passwordEncoder.encode(password));
            apprenant.setRole(Role.APPRENANT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        userRepository.save(apprenant);
        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }

    @PostMapping("/me")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Map<String, Object> userInfo = new HashMap<>();

        if (user.getRole().equals(Role.APPRENANT)) {
            Apprenant apprenant = (Apprenant) user;
            userInfo.put("telephone", apprenant.getTelephone());
            userInfo.put("localisation", apprenant.getLocalisation());
            userInfo.put("sexe", apprenant.getSexe());
            userInfo.put("date_naissance", apprenant.getDate_naissance());
            userInfo.put("divers", apprenant.getDivers());
        } else if (user.getRole().equals(Role.ADMIN)) {
            Administrateur admin = (Administrateur) user;
            userInfo.put("email", admin.getEmail());
            userInfo.put("hopital", admin.getHopital());
        }

        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("nom", user.getNom());
        userInfo.put("prenom", user.getPrenom());

        if (user.getProfil() != null) {
            String base64Image = Base64.getEncoder().encodeToString(user.getProfil());
            userInfo.put("profil", "data:image/jpeg;base64," + base64Image);
        } else {
            userInfo.put("profil", null);
        }

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/countUsers")
    public Long getTotalUsers() {
        return this.userRepository.count();
    }

    @GetMapping(value = "/findAllAdmins", headers = "Accept=application/json")
    public ResponseEntity<List<Map<String, Object>>> findAllAdmins() {
        List<Administrateur> users = userRepository.findAllAdmins();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Administrateur user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("nom", user.getNom());
            map.put("prenom", user.getPrenom());
            map.put("email", user.getEmail());
            map.put("hopital", user.getHopital());
            map.put("statut", user.isStatut());

            if (user.getProfil() != null) {
                String base64Image = Base64.getEncoder().encodeToString(user.getProfil());
                map.put("profil", "data:image/jpeg;base64," + base64Image);
            } else {
                map.put("profil", null);
            }

            responseList.add(map);
        }
        return ResponseEntity.ok(responseList);

    }

    @GetMapping(value = "/findAllApprenants", headers = "Accept=application/json")
    public ResponseEntity<List<Map<String, Object>>> findAllApprenants() {
        List<Apprenant> users = userRepository.findAllApprenants();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Apprenant user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("nom", user.getNom());
            map.put("prenom", user.getPrenom());
            map.put("telephone", user.getTelephone());
            map.put("localisation", user.getLocalisation());
            map.put("sexe", user.getSexe());
            map.put("date_naissance", user.getDate_naissance());
            map.put("divers", user.getDivers());
            map.put("statut", user.isStatut());

            if (user.getProfil() != null) {
                String base64Image = Base64.getEncoder().encodeToString(user.getProfil());
                map.put("profil", "data:image/jpeg;base64," + base64Image);
            } else {
                map.put("profil", null);
            }

            responseList.add(map);
        }
        return ResponseEntity.ok(responseList);

    }

    @PutMapping(value = "/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");

        }
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("Ancien mot de passe incorrect !");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok("Mot de passe changé avec succès !");
    }

    @PutMapping(value = "/changeProfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeProfil(
            @RequestParam("profil") MultipartFile profil,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");

        }
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        try {
            user.setProfil(profil.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        userRepository.save(user);
        return ResponseEntity.ok("Profil changé avec succès !");
    }

    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            this.userRepository.deleteById(id);
            return ResponseEntity.ok().body("Utilisateur supprimé");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Une erreur interne est survenue");
        }
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé !");
        }

        user.setStatut(request.get("statut"));
        userRepository.save(user);

        return ResponseEntity.ok("Statut mis à jour avec succès !");
    }

}
