package com.example.E_care.Utilisateurs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle", nullable = false, unique = true)
    @Enumerated(EnumType.STRING) // Pour stocker les rôles sous forme de texte
    private RoleName libelle;
}


