package com.example.E_care.Alerte.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.E_care.Alerte.service.InterventionService;
import com.example.E_care.Utilisateurs.dao.UserDao;
import com.example.E_care.Utilisateurs.models.User;
import com.example.E_care.Alerte.model.Intervention;




@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "free/service/intervention")
public class InterventionController{

    @Autowired
    @Qualifier(value = "interventionService")
    private InterventionService interventionService;

    @Autowired
    private UserDao userRepository;

    @GetMapping(value = "/findAllByAdmin", headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<List<Intervention>> findAll(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        List<Intervention> interventions = interventionService.findAllByAdmin(user);
        
        return ResponseEntity.ok(interventions);
    }

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<List<Intervention>> findAll() {
        List<Intervention> interventions = interventionService.findAll();
        
        return ResponseEntity.ok(interventions);
    }

   @GetMapping(value = "/findAllByUser", headers = "Accept=application/json")
   @Transactional
    public ResponseEntity<List<Map<String, Object>>> findAllByUser(Authentication authentication) {
    User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    List<Intervention> interventions = interventionService.findAllByUser(user);
    List<Map<String, Object>> responseList = new ArrayList<>();

    for (Intervention intervention : interventions) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", intervention.getId());
        map.put("titre", intervention.getAlerte().getTitre());
        map.put("contenu", intervention.getAlerte().getContenu());
        map.put("localisation", intervention.getAlerte().getLocalisation());
        map.put("hopital", intervention.getHopital().getNom());
        map.put("statut", intervention.getStatut());
        map.put("date", intervention.getDate());


        responseList.add(map);
    }
    return ResponseEntity.ok(responseList);
}


    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<?>delete(@PathVariable Long id){
        try{
            Boolean result = this.interventionService.deleteById(id);

            if(result){
                return ResponseEntity.ok().body("intervention supprimé avec succès");
            }else{
                return ResponseEntity.badRequest().body("echec de la suppression");
            }
        }catch(Exception e) {
            return ResponseEntity.internalServerError().body("erreur interne");
        }
    }

    @GetMapping(value = "/findAllByHopital/{id}", headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<List<Intervention>> findAllByHopital(@PathVariable Long id) {
        List<Intervention> interventions = interventionService.findByHopital(id);
        
        return ResponseEntity.ok(interventions);
    }
    

@PostMapping(value = "/accepter/{id}", headers = "Accept=application/json")
@Transactional
public ResponseEntity<?> accepterIntervention(@PathVariable Long id, Authentication authentication) {
    User user = userRepository.findByUsername(authentication.getName()).get();
    try {
        Intervention intervention = this.interventionService.accepterIntervention(id, user);
        if (intervention != null) {
            return ResponseEntity.ok(intervention);
        } else {
            return ResponseEntity.badRequest().body("echec de l'acceptation de l'intervention");
        }
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("erreur interne");
    }
}

@PostMapping(value = "/interompre/{id}", headers = "Accept=application/json")
@Transactional
public ResponseEntity<?> refuserIntervention(@PathVariable Long id) {
    try {
        Intervention intervention = this.interventionService.interompreIntervention(id);
        if (intervention != null) {
            return ResponseEntity.ok(intervention);
        } else {
            return ResponseEntity.badRequest().body("echec de l'acceptation de l'intervention");
        }
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("erreur interne");
    }
}

@PostMapping(value = "/reprendre/{id}", headers = "Accept=application/json")
@Transactional
public ResponseEntity<?> reprendreIntervention(@PathVariable Long id) {
    try {
        Intervention intervention = this.interventionService.reprendreIntervention(id);
        if (intervention != null) {
            return ResponseEntity.ok(intervention);
        } else {
            return ResponseEntity.badRequest().body("echec de l'acceptation de l'intervention");
        }
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("erreur interne");
    }
}

@PostMapping(value = "/achever/{id}", headers = "Accept=application/json")
@Transactional
public ResponseEntity<?> acheverIntervention(@PathVariable Long id) {
    try {
        Intervention intervention = this.interventionService.acheverIntervention(id);
        if (intervention != null) {
            return ResponseEntity.ok(intervention);
        } else {
            return ResponseEntity.badRequest().body("echec de l'acceptation de l'intervention");
        }
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("erreur interne");
    }
}

@GetMapping("/countIntervention")
public Long getTotalIntervention(){
    return interventionService.getTotalIntervention();
}
}

