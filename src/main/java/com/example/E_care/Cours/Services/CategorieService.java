package com.example.E_care.Cours.Services;

import com.example.E_care.Cours.models.Categorie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "categorieService")
public interface CategorieService {
    public List<Categorie> findAll();

    public Categorie save(Categorie categorie);

    public Boolean deleteById(Long id);

    public Categorie update(Long id, Categorie categorie);


}
