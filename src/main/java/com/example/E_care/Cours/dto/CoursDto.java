package com.example.E_care.Cours.dto;


import com.example.E_care.media.dto.MediaDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursDto {
    private Long id;
    private String titre;
    private String contenu;
    private Long categorieId;
    private List<MediaDto> medias; // Liste des médias avec type
}

