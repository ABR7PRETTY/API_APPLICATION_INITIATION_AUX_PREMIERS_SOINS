package com.example.E_care.Cours.controllers;

import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.Services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "service/categorie")
public class CategorieController {

    @Autowired
    @Qualifier(value = "categorieService")
    private CategorieService categorieService;

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        try {
            categories = this.categorieService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @PutMapping(value = "admin/update/{id}", headers = "Accept=application/json")
    public ResponseEntity<Categorie> update(@PathVariable("id") Long id, @RequestBody Categorie categorie){
        return ResponseEntity.status(HttpStatus.OK).body(this.categorieService.update(id, categorie));
    }

    @PostMapping(value = "admin/save", headers = "Accept=application/json")
    public Categorie save(@RequestBody Categorie categorie) {
        try {
            categorie = this.categorieService.save(categorie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorie;
    }

    @DeleteMapping(value="admin/delete/{id}", headers ="Accept=application/json")
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




}


