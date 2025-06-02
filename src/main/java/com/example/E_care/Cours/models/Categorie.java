package com.example.E_care.Cours.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre", nullable = false, unique = true)
    private String titre;

    @Column(name = "description", nullable = false)
    private String description;
    

    @Lob  // Large Object Binary
    @Basic(fetch = FetchType.LAZY) // Permet d'optimiser la récupération des images
    @Column(columnDefinition = "bytea") // Définit le type BLOB pour stocker de grandes images
    private byte[] image;


}