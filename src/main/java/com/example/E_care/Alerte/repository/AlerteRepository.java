package com.example.E_care.Alerte.repository;

import com.example.E_care.Alerte.model.Alerte;
import com.example.E_care.Utilisateurs.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface AlerteRepository extends JpaRepository<Alerte,Long> {
    public List<Alerte> findByUser(User user);


    @Query("SELECT a FROM Alerte a WHERE a.statut = true")
    public List<Alerte> findActiveAlertes();
}