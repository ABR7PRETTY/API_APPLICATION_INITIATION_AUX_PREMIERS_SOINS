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

    @Column(name = "description", nullable = true)
    private String description;



}