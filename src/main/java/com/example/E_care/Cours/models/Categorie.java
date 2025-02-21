package com.example.E_care.Cours.models;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity 
@Getter @Setter
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
    

    @Column(name = "image", nullable = false)
    private String image; 


}