package com.example.E_care.Alerte.repository;

import com.example.E_care.Alerte.model.Alerte;
import com.example.E_care.Alerte.model.Intervention;
import com.example.E_care.Urgence.models.Hopital;
import com.example.E_care.Utilisateurs.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterventionRepository extends JpaRepository<Intervention,Long> {

    public List<Intervention> findByHopital(Hopital hopital);

    public Optional<Intervention> findByAlerte(Alerte alerte);

    @Query("SELECT i FROM Intervention i WHERE i.alerte.user = :user")
    public List<Intervention> findInterventionsByUser(@Param("user") User user);

    @Query("SELECT i FROM Intervention i WHERE i.statut = Statut.En_traitement")
    public List<Intervention> findActivesInterventions();
}