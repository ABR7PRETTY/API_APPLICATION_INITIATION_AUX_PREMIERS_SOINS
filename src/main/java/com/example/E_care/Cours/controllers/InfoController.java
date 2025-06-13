package com.example.E_care.Cours.controllers;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.E_care.Cours.Services.InfoService;
import com.example.E_care.Cours.dto.InfoDto;
import com.example.E_care.Cours.models.Information;
import com.example.E_care.media.models.Media;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "free/service/info")
public class InfoController {

    @Autowired
    @Qualifier(value = "infoService")
    private InfoService infoService;

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<List<Map<String, Object>>> findAll() {
    List<Information> informations = infoService.findInformation();
    List<Map<String, Object>> responseList = new ArrayList<>();

    for (Information info : informations) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", info.getId());
        map.put("titre", info.getTitre());
        map.put("contenu", info.getContenu());
        map.put("date_ajout", info.getDateAjout());
        map.put("date_fin", info.getDateFin());
        

        List<String> medias= new ArrayList<>();

        for (Media media : info.getMedias()){
            medias.add("data:image/jpeg;base64," +Base64.getEncoder().encodeToString(media.getFichier()));
        }

        map.put("medias", medias);

        responseList.add(map);
    }
    return ResponseEntity.ok(responseList);
}


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Information save(@RequestParam("titre") String titre,
        @RequestParam("contenu") String contenu,
        @RequestParam("date_fin") LocalDate date_fin,
        @RequestParam("files") List<MultipartFile> mediaFiles) {
    
    InfoDto infoDto = new InfoDto();
    infoDto.setTitre(titre);
    infoDto.setContenu(contenu);
    infoDto.setDate_fin(date_fin);

    return infoService.save(infoDto, mediaFiles);}
    
    @DeleteMapping(value = "delete/{id}", headers = "Accept=application/json")
    public Boolean deleteById(@PathVariable Long id) {
        Boolean result = false;
        try {
            result = this.infoService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return result;
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Information update(@PathVariable Long id,
     @RequestParam String titre,
     @RequestParam String contenu,
     @RequestParam LocalDate date_fin,
     @RequestParam List<MultipartFile> files){
        Information information = new Information();
        try{
            information = this.infoService.update(id, titre, contenu, date_fin, files);
        }catch(Exception e){
            e.printStackTrace();
        }
        return information;
    }

    
}
