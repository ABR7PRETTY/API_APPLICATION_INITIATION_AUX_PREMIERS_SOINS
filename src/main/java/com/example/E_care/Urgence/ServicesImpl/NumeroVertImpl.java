package com.example.E_care.Urgence.ServicesImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.E_care.Urgence.Services.NumeroVertService;
import com.example.E_care.Urgence.models.NumerosVerts;
import com.example.E_care.Urgence.dao.NumeroVertDao;

@Service(value = "numeroVertService")
public class NumeroVertImpl implements NumeroVertService{

    @Autowired
    private NumeroVertDao numeroVertDao;

    @Override
    public List<NumerosVerts> findAll() {
        return this.numeroVertDao.findAll();
    }

    @Override
    public NumerosVerts save(NumerosVerts numeroVert) {
        return this.numeroVertDao.save(numeroVert);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(this.numeroVertDao.existsById(id)){
            this.numeroVertDao.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public NumerosVerts update(Long id, NumerosVerts numeroVert) {
        NumerosVerts NumeroExistant = this.numeroVertDao.findById(id).orElseThrow(() -> new RuntimeException("NumeroVert not found"));
        BeanUtils.copyProperties(numeroVert, NumeroExistant);
        return this.numeroVertDao.save(NumeroExistant);
    }
    
}
