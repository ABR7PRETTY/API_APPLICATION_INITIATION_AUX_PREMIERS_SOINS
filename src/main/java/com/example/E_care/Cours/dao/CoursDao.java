package com.example.E_care.Cours.dao;

import com.example.E_care.Cours.models.Cours;
import com.example.E_care.Cours.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoursDao extends JpaRepository<Cours,Long> {
    public List<Cours> findByCategorie(Categorie categorie);
    
}