package com.example.E_care.Cours.controllers;

import com.example.E_care.Cours.models.Cours;
import com.example.E_care.Cours.dto.CoursDto;
import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.Services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "apprenant/services/cours")
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

    @GetMapping(value = "/findByCategorie/{id}" , headers = "Accept=application/json")
    public List<Cours> findByCategorie(@PathVariable Long id) {
        Categorie categorie =  new Categorie();
        List<Cours> cours = new ArrayList<>();
        try {
            categorie.setId(id);
            cours = this.coursService.findByCategorie(categorie);
        } catch (Exception e) {
            e.printStackTrace();
    }
        return cours;

}

    @PostMapping(value = "admin/save", headers = "Accept=application/json")
    public Cours save(@RequestBody CoursDto coursDto){
        Cours cours = new Cours();
    
        try{
            cours = this.coursService.save(coursDto);
        } catch( Exception e){
            e.printStackTrace();
        }
        return cours;

    }
    

    @PutMapping(value = "admin/update/{id}", headers = "Accept=application/json")
    public Cours update(@PathVariable Long id, @RequestBody CoursDto coursDto){
        Cours cours = new Cours();
        try{
            cours = this.coursService.update(id, coursDto);
        }catch(Exception e){
            e.printStackTrace();
        }
        return cours;
    }

    @DeleteMapping(value = "admin/delete/{id}", headers = "Accept=application/json")
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
}

