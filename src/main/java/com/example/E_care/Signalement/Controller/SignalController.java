package com.example.E_care.Signalement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.E_care.Signalement.Service.SignalService;
import org.springframework.web.bind.annotation.*;
import com.example.E_care.Signalement.models.Signal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@CrossOrigin
@RequestMapping("service/signal")
public class SignalController {
    
    @Autowired
    private SignalService signalService;


    @PostMapping(value = "/save", headers = "Accept=application/json")
    public Signal save(@RequestBody Signal signal){
        Signal newSignal = new Signal();
        try{
            newSignal = this.signalService.save(signal);
        }catch(Exception e){
            e.printStackTrace();
        }
        return newSignal;

    }

    @GetMapping(value = "/findAll", headers = "Accept=application/json")
    public List<Signal> findAll() {
        List<Signal> signals = this.signalService.findAll();
        return signals;
    }

    @PutMapping(value = "update/{id}" , headers = "Accept=application/json")
    public Signal update(@PathVariable Long id, @RequestBody Signal signal){
        Signal updateSignal = new Signal();
        try{
            updateSignal = this.signalService.update(id, signal);
        }catch(Exception e){
            e.printStackTrace();
        }
        return updateSignal;
    }

    @DeleteMapping(value = "delete/{id}", headers = "Accept=application/json")
    public Boolean deleteById(@PathVariable Long id){
        Boolean result = false;
        try{
            result = this.signalService.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
}
}
