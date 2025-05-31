package com.example.E_care.Cours.Services;

import com.example.E_care.Cours.dto.CoursDto;
import com.example.E_care.Cours.models.Cours;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service(value = "coursService")
public interface CoursService {
    public List<Cours> findAll();

    public Cours save(CoursDto coursDto, List<MultipartFile> medias);

    public Boolean deleteById (Long id);

    public Cours update(Long id, String titre, String contenu, Long categorieId, List<MultipartFile> files );

    public List<Cours> findByCategorie(Long categorieId);

    public Long getTotalCours();

    
}