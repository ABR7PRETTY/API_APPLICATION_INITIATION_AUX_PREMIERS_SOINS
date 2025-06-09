package com.example.E_care.Utilisateurs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Héritage dans une seule table
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
@Table(name = "Utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "statut", nullable = false)
    private boolean statut = true;

    @Lob  // Large Object Binary
    @Basic(fetch = FetchType.LAZY) // Permet d'optimiser la récupération des images
    @Column(nullable= true) // Définit le type BLOB pour stocker de grandes images columnDefinition = "LONGBLOB"
    private byte[] profil;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

   

}