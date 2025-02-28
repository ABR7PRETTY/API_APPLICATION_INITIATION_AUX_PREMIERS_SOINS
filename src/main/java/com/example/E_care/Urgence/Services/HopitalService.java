package com.example.E_care.Urgence.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.E_care.Urgence.models.Hopital;

@Service(value = "hopitalService")
public interface HopitalService {

    public List<Hopital> findAll();

    public Hopital save(Hopital hopital);

    public Boolean deleteById (Long id);

    public Hopital update(Long id, Hopital hopital);
    
}