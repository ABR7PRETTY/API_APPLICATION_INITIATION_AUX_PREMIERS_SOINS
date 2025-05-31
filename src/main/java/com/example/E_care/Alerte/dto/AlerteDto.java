package com.example.E_care.Alerte.dto;


import com.example.E_care.media.dto.MediaDto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlerteDto {
    private Long id;
    private String titre;
    private String contenu;
    private String localisation;
    private boolean statut;
    private Double latitude;
    private Double longitude;
    private Long userId;
    private List<MediaDto> medias; // Liste des médias avec type
}

