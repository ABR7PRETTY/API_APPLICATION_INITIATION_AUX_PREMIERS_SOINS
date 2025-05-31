package com.example.E_care.Cours.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.example.E_care.Cours.Services.CoursService;
import com.example.E_care.Cours.dto.CoursDto;
import com.example.E_care.Cours.models.Cours;
import com.example.E_care.media.models.Media;



@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "free/service/cours")
public class CoursController{

    @Autowired
    @Qualifier(value = "coursService")
    private CoursService coursService;

    @GetMapping(value = "/findAll" , headers = "Accept=application/json")
    public List<Cours> findAll() {
        List<Cours> cours = new ArrayList<>();
        try {
            cours = this.coursService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cours;
    } 

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Cours save(@RequestParam("titre") String titre,
        @RequestParam("contenu") String contenu,
        @RequestParam("categorieId") Long categorieId,
        @RequestParam("files") List<MultipartFile> mediaFiles) {
    
    CoursDto coursDto = new CoursDto();
    coursDto.setTitre(titre);
    coursDto.setContenu(contenu);
    coursDto.setCategorieId(categorieId);

    return coursService.save(coursDto, mediaFiles);}
    

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Cours update(@PathVariable Long id,
     @RequestParam String titre,
     @RequestParam String contenu,
     @RequestParam Long categorieId,
     @RequestParam List<MultipartFile> files){
        Cours cours = new Cours();
        try{
            cours = this.coursService.update(id, titre, contenu, categorieId, files);
        }catch(Exception e){
            e.printStackTrace();
        }
        return cours;
    }

    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<?>delete(@PathVariable Long id){
        try{
            Boolean result = this.coursService.deleteById(id);

            if(result){
                return ResponseEntity.ok().body("cours supprimé avec succès");
            }else{
                return ResponseEntity.badRequest().body("echec de la suppression");
            }
        }catch(Exception e) {
            return ResponseEntity.internalServerError().body("erreur interne");
        }
    }

    @GetMapping(value = "/findAllByCategorie/{id}", headers = "Accept=application/json")
    public ResponseEntity<List<Map<String, Object>>> findAllByCategorie(@PathVariable Long id) {
    List<Cours> cours = coursService.findByCategorie(id);
    List<Map<String, Object>> responseList = new ArrayList<>();

    for (Cours cour : cours) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", cour.getId());
        map.put("titre", cour.getTitre());
        map.put("contenu", cour.getContenu());
        

        List<String> medias= new ArrayList<>();

        for (Media media : cour.getMedias()){
            medias.add("data:image/jpeg;base64," +Base64.getEncoder().encodeToString(media.getFichier()));
        }

        Collections.reverse(medias);
        map.put("medias", medias);

        responseList.add(map);
    }
    return ResponseEntity.ok(responseList);
}

@GetMapping("/countCours")
public Long getTotalCours(){
    return coursService.getTotalCours();
}
}

