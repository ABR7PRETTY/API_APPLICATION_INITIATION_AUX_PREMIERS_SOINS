package com.example.E_care.Alerte.model;


import java.util.List;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.example.E_care.media.models.Media;
import com.example.E_care.Utilisateurs.models.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "Alerte")
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre", nullable = false, unique = true)
    private String titre;

    @Column(name = "contenu", nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    
    @Column(name = "localisation", nullable = false)
    private String localisation;

    @Column(name = "date_alerte", nullable = true)
    @CreationTimestamp
    private Date date;

    @Column(name = "statut", nullable = false)
    private boolean statut;

    @OneToMany(mappedBy = "alertes", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Media> medias;


}