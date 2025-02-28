package com.example.E_care.Cours.controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.E_care.Cours.Services.InfoService;
import com.example.E_care.Cours.dto.InfoDto;
import com.example.E_care.Cours.models.Information;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "service/info")
public class InfoController {

    @Autowired
    @Qualifier(value = "infoService")
    private InfoService infoService;

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    public List<Information> findAll() {
        List<Information> informations = new ArrayList<>();
        try {
            informations = this.infoService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
                return informations;
            
        }


    @PostMapping(value = "admin/save", headers = "Accept=application/json")
    public Information save(@RequestBody InfoDto infoDto) {
        Information information = new Information();
        try {
            information = this.infoService.save(infoDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return information;
    }
    
    @DeleteMapping(value = "admin/delete/{id}", headers = "Accept=application/json")
    public Boolean deleteById(@PathVariable Long id) {
        Boolean result = false;
        try {
            result = this.infoService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return result;
    }

    @PutMapping(value = "admin/update/{id}", headers = "Accept=application/json")
    public Information update(@PathVariable Long id, @RequestBody InfoDto infoDto) {
        Information information = new Information();
        try {
            information = this.infoService.update(id, infoDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return information;
    }
    
}
