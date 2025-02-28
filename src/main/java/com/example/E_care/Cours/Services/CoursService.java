package com.example.E_care.Cours.Services;

import com.example.E_care.Cours.dto.CoursDto;
import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.models.Cours;

import org.springframework.stereotype.Service;
import java.util.List;

@Service(value = "coursService")
public interface CoursService {
    public List<Cours> findAll();

    public Cours save(CoursDto coursDto);

    public Boolean deleteById (Long id);

    public Cours update(Long id, CoursDto  coursDto);

    public List<Cours> findByCategorie(Categorie categorie);

    
}