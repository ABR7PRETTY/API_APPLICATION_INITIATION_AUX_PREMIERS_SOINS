package com.example.E_care.Cours.dto;


import com.example.E_care.media.dto.MediaDto;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoDto {
    private Long id;
    private String titre;
    private String contenu;
    private Date date_ajout;
    private LocalDate date_fin;
    private List<MediaDto> medias; // Liste des médias avec type
}

