package com.example.E_care.media.models;

import com.example.E_care.Alerte.model.Alerte;
import com.example.E_care.Cours.models.Cours;
import com.example.E_care.Cours.models.Information;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob  // Large Object Binary
    @Basic(fetch = FetchType.LAZY) // Permet d'optimiser la récupération des images
    @Column(nullable = false) // Définit le type BLOB pour stocker de grandes images columnDefinition = "LONGBLOB"
    private byte[] fichier;

    @Column(name = "type", nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType; // COURS ou INFORMATION

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "cours_id", nullable = true)
    private Cours cours;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "information_id", nullable = true)
    private Information informations;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "alerte_id", nullable = true)
    private Alerte alertes;

    
}
