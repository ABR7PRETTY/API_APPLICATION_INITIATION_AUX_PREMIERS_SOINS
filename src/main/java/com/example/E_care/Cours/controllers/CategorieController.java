package com.example.E_care.Cours.controllers;

import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.Services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "free/service/categorie")
public class CategorieController {

    @Autowired
    @Qualifier(value = "categorieService")
    private CategorieService categorieService;

    @GetMapping(value = "/findCategorie/{id}", headers = "Accept=application/json")
    public Categorie findCategorie(@PathVariable Long id) {
    return categorieService.findCategorie(id); }

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    public ResponseEntity<List<Map<String, Object>>> findAll() {
    List<Categorie> categories = categorieService.findAll();
    List<Map<String, Object>> responseList = new ArrayList<>();

    for (Categorie categorie : categories) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", categorie.getId());
        map.put("titre", categorie.getTitre());
        map.put("description", categorie.getDescription());

        if (categorie.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(categorie.getImage());
            map.put("image", "data:image/jpeg;base64," + base64Image);
        } else {
            map.put("image", null);
        }

        responseList.add(map);
    }

    return ResponseEntity.ok(responseList);
}

@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public Categorie updateCategorie(
        @PathVariable Long id,
        @RequestParam("titre") String titre,
        @RequestParam("description") String description,
        @RequestParam(value = "image", required = false) MultipartFile image) {
    try {
        return categorieService.update(id, titre, description, image);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Categorie save(@RequestParam("titre") String titre, @RequestParam("description") String description, @RequestParam(value = "image" , required = false) MultipartFile image) {
        Categorie categorie = new Categorie();
        try {
            categorie.setTitre(titre);
            categorie.setDescription(description);
            categorie.setImage(image.getBytes());
            categorie = this.categorieService.save(categorie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorie;
    }

    @DeleteMapping(value="delete/{id}", headers ="Accept=application/json")
    public ResponseEntity<?>delete(@PathVariable Long id){
        try{
            Boolean resultat = this.categorieService.deleteById(id);
            if(resultat){
                return  ResponseEntity.ok().body(" La categorie a été supprimée avec succès");
            }else{
                return ResponseEntity.badRequest().body("Echec de la suppression");
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Une erreur interne est survenue");
        }
    }

    @GetMapping("/countCategories")
    public Long getTotalcategories(){
        return this.categorieService.getTotalCategorie();
    }


}


