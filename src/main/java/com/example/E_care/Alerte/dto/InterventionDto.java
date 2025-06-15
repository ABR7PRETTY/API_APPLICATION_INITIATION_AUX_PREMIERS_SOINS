package com.example.E_care.Alerte.dto;

import com.example.E_care.Alerte.model.Statut;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InterventionDto {
    
    private Long id;
    private Long alerteId;
    private String titre;
    private String contenu;
    private String localisation;
    private Long userId;
    private Date date;
    private Statut statutIntervention;
    private String hopital;

    
    
}
