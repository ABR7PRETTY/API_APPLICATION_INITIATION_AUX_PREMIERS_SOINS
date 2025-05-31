package com.example.E_care.Urgence.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity 
@Getter @Setter
@Table(name = "hopital")
public class Hopital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @Column(name = "localisation", nullable = true)
    private String localisation;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    @Column(name = "telephone", nullable = true)
    private String telephone;


    @Column(name = "description", nullable = true)
    private String description;

}