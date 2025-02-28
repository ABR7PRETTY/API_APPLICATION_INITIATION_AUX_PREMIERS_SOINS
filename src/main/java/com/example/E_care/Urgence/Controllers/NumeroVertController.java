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

import com.example.E_care.Urgence.Services.NumeroVertService;
import com.example.E_care.Urgence.models.NumerosVerts;

@RestController
@CrossOrigin
@RequestMapping(value = "service/numeroVert")
public class NumeroVertController {
    @Autowired
    @Qualifier("numeroVertService")
    private NumeroVertService numeroVertService;

    @GetMapping(value = "/findAll" , headers = "Accept=Application/json")
    public List<NumerosVerts> findAll(){
        List<NumerosVerts> numeros = new ArrayList<> ();
        try{
            numeros = this.numeroVertService.findAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return numeros;
    }

    @PostMapping(value = "/save" , headers = "Accept=Application/json")
    public NumerosVerts save(@RequestBody NumerosVerts numerosVerts){
        NumerosVerts nouveauNumero = new NumerosVerts();
        try{
            nouveauNumero = this.numeroVertService.save(numerosVerts);
        }catch(Exception e){
            e.printStackTrace();
        }
        return nouveauNumero;
    }

    @PutMapping(value = "update/{id}" , headers = "Accept=Application/json")
    public NumerosVerts save(@PathVariable Long id, @RequestBody NumerosVerts numerosVerts){
        NumerosVerts updateNumero = new NumerosVerts();
        try{
            updateNumero = this.numeroVertService.update(id,numerosVerts);
        }catch(Exception e){
            e.printStackTrace();
        }
        return updateNumero;
    }

    @DeleteMapping(value = "delete/{id}", headers = "Accept=Application/json")
    public Boolean delete(@PathVariable Long id){
        Boolean result = false;
        try{
            result = this.numeroVertService.deleteById(id);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
