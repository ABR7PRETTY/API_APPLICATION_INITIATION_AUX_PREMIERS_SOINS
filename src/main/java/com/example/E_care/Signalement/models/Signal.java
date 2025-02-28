package com.example.E_care.Signalement.models;

import com.example.E_care.Utilisateurs.models.Apprenant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "Signal")
public class Signal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "localisation", nullable = false)
    private String localisation;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "statut", nullable = false)
    private String etat;

    @ManyToOne
    @JoinColumn(name = "apprenant_id", nullable = false)
    private Apprenant apprenant;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true)
    private Apprenant administrateur;

}