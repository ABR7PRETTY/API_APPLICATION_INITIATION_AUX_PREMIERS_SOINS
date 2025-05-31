package com.example.E_care.Urgence.Controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.E_care.Urgence.Services.HopitalService;
import com.example.E_care.Urgence.models.Hopital;

@RestController
@CrossOrigin
@RequestMapping("free/service/hopital")
public class HopitalController {

    @Autowired
    @Qualifier(value = "hopitalService")
    private HopitalService hopitalService;

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    public List<Hopital> findAll(){
        List<Hopital> hopitaux = new ArrayList<>();
        try{
            hopitaux = this.hopitalService.findAll();
        }catch(Exception e){
            e.printStackTrace();
        }

        return hopitaux;
    }

    @PostMapping(value = "/save", headers = "Accept=application/json")
    public Hopital save(@RequestBody Hopital hopital){
        try{
            hopital.setId(null);
            hopital = this.hopitalService.save(hopital);
        }catch(Exception e){
            e.printStackTrace();
        }
        return hopital;

    }

    @PutMapping(value = "update/{id}", headers = "Accept=application/json" )
    public Hopital update(@PathVariable Long id, @RequestBody Hopital hopital){
        Hopital updateHopital = new Hopital();
        try{
            updateHopital = this.hopitalService.update(id, hopital);
        }catch(Exception e){
            e.printStackTrace();
        }
        return updateHopital;
    }

    @DeleteMapping(value = "delete/{id}", headers = "Accept=application/json")
    public Boolean delete(@PathVariable Long id){
        Boolean result = false;
        try{
            result = this.hopitalService.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
}
