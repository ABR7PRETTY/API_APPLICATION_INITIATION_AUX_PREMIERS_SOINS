package com.example.E_care.Utilisateurs.models;


import com.example.E_care.Urgence.models.Hopital;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("ADMIN") // Indique que c'est un Admin dans la table User
public class Administrateur extends User {

    @Column(name = "email", nullable = true)
    private String email;
    
    @OneToOne
    @JoinColumn(name = "hopital", nullable = true, unique = true)
    private Hopital Hopital;
}
