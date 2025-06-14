package com.example.E_care.Alerte.service;

import com.example.E_care.Alerte.dto.InterventionDto;
import com.example.E_care.Alerte.model.Intervention;
import com.example.E_care.Utilisateurs.models.User;

import org.springframework.stereotype.Service;


import java.util.List;

@Service(value = "interventionService")
public interface InterventionService {
    public List<InterventionDto> findAllByAdmin(User user);

    public List<Intervention> findAllByUser(User user);

    public List<Intervention> findAll();

    public Boolean deleteById (Long id);

    public List<Intervention> findByHopital(Long id);

    public Intervention accepterIntervention(Long id, User user);

    public Intervention interompreIntervention(Long id);

    public Intervention reprendreIntervention(Long id);

    public Intervention acheverIntervention(Long id);

    public Long getTotalIntervention();

    
}