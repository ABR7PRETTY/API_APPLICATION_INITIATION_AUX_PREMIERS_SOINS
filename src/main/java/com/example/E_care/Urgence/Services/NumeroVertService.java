package com.example.E_care.Urgence.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.E_care.Urgence.models.NumerosVerts;

@Service(value = "numeroVertService")
public interface NumeroVertService {

    public List<NumerosVerts> findAll();

    public NumerosVerts save(NumerosVerts numeroVert);

    public Boolean deleteById (Long id);

    public NumerosVerts update(Long id, NumerosVerts numeroVert);

    
}