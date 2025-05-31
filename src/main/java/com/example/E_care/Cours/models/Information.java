package com.example.E_care.Cours.models;

import java.util.List;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.example.E_care.media.models.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "Information")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre", nullable = false, unique = true)
    private String titre;

    @Column(name = "contenu", nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "date_ajout", nullable = false, updatable = false)
    @CreationTimestamp // Ajoute automatiquement la date actuelle lors de l'ajout
    private LocalDate dateAjout;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @OneToMany(mappedBy = "informations", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> medias;


}