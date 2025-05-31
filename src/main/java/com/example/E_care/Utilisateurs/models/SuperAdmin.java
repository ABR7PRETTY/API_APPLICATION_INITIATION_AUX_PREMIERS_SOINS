package com.example.E_care.Utilisateurs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("SUPERADMIN") // Indique que c'est un Admin dans la table User
public class SuperAdmin extends User {
    @Column(name = "email", nullable = true)
    private String email;
}
