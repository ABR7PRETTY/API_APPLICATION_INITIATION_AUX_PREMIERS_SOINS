package com.example.E_care.Cours.dao;

import com.example.E_care.Cours.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategorieDao extends JpaRepository<Categorie, Long>{
    public List<Categorie> getByTitre(String titre);
}
