package com.example.E_care.Alerte.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.E_care.Alerte.service.AlerteService;
import com.example.E_care.Utilisateurs.dao.UserDao;
import com.example.E_care.Utilisateurs.models.User;
import com.example.E_care.Alerte.dto.AlerteDto;
import com.example.E_care.Alerte.model.Alerte;
import com.example.E_care.media.models.Media;



@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "free/service/alerte")
public class AlerteController{

    @Autowired
    @Qualifier(value = "alerteService")
    private AlerteService alerteService;

    @Autowired
    private UserDao userRepository;

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Map<String, Object>>> findAll() {
        List<Alerte> alertes = alerteService.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Alerte alerte : alertes) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", alerte.getId());
            map.put("titre", alerte.getTitre());
            map.put("contenu", alerte.getContenu());
            map.put("localisation", alerte.getLocalisation());
            map.put("statut", alerte.isStatut());
            map.put("date", alerte.getDate());
            map.put("user", alerte.getUser());
            map.put("latitude", alerte.getLatitude());
            map.put("longitude", alerte.getLongitude());

            List<String> medias= new ArrayList<>();

            for (Media media : alerte.getMedias()){
                medias.add("data:image/jpeg;base64," +Base64.getEncoder().encodeToString(media.getFichier()));
            }

            map.put("medias", medias);

            responseList.add(map);
        }
        return ResponseEntity.ok(responseList);
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public Alerte save(@RequestParam("titre") String titre,
        @RequestParam("contenu") String contenu,
        @RequestParam("localisation") String localisation,
        @RequestParam("latitude") Double latitude,
        @RequestParam("longitude") Double longitude,
        @RequestParam(name = "files", required = false) List<MultipartFile> mediaFiles,
        Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

                
    
    AlerteDto alerteDto = new AlerteDto();
    alerteDto.setTitre(titre);
    alerteDto.setContenu(contenu);
    alerteDto.setLocalisation(localisation);
    alerteDto.setLatitude(latitude);
    alerteDto.setLongitude(longitude);
    alerteDto.setUserId(user.getId());

    return alerteService.save(alerteDto, mediaFiles != null ? mediaFiles : new ArrayList<>());}
    

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(readOnly = true)
    public Alerte update(@PathVariable Long id,
     @RequestParam String titre,
     @RequestParam String contenu,
     @RequestParam String localisation,
    @RequestParam Double latitude,
    @RequestParam Double longitude,
     @RequestParam Long userId,
     @RequestParam(required = false) List<MultipartFile> files){
        Alerte alerte = new Alerte();
        AlerteDto alerteDto = new AlerteDto();
        alerteDto.setId(id);
        alerteDto.setTitre(titre);
        alerteDto.setContenu(contenu);
        alerteDto.setLocalisation(localisation);
        alerteDto.setLatitude(latitude);
        alerteDto.setLongitude(longitude);
        alerteDto.setUserId(userId);
        try{
            alerte = this.alerteService.update(alerteDto, files != null ? files : new ArrayList<>());
        }catch(Exception e){
            e.printStackTrace();
        }
        return alerte;
    }

    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<?>delete(@PathVariable Long id){
        try{
            Boolean result = this.alerteService.deleteById(id);

            if(result){
                return ResponseEntity.ok().body("alerte supprimé avec succès");
            }else{
                return ResponseEntity.badRequest().body("echec de la suppression");
            }
        }catch(Exception e) {
            return ResponseEntity.internalServerError().body("erreur interne");
        }
    }

    @GetMapping(value = "/findAllByUser", headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<List<Map<String, Object>>> findAllByUser(Authentication authentication) {
    User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    List<Alerte> alerte = alerteService.findByUser(user.getId());
    List<Map<String, Object>> responseList = new ArrayList<>();

    for (Alerte alertes : alerte) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", alertes.getId());
        map.put("titre", alertes.getTitre());
        map.put("contenu", alertes.getContenu());
        map.put("latitude", alertes.getLatitude());
        map.put("longitude", alertes.getLongitude());
        map.put("localisation", alertes.getLocalisation());
        map.put("statut", alertes.isStatut());
        map.put("date", alertes.getDate());
        

        List<String> medias= new ArrayList<>();

        for (Media media : alertes.getMedias()){
            medias.add("data:image/jpeg;base64," +Base64.getEncoder().encodeToString(media.getFichier()));
        }

        map.put("medias", medias);

        responseList.add(map);
    }
    return ResponseEntity.ok(responseList);
}

@GetMapping("/countAlerte")
@Transactional(readOnly = true)
public Long getTotalAlerte(){
    return alerteService.getTotalAlerte();
}
}

