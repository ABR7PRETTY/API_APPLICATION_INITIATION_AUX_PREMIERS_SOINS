package com.example.E_care.Utilisateurs.models;


import com.example.E_care.Urgence.models.Hopital;
import jakarta.persistence.*;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("ADMIN") // Indique que c'est un Admin dans la table User
public class Administrateur extends User {
    
    @OneToOne
    @JoinColumn(name = "hopital", nullable = true, unique = true)
    private Hopital Hopital;
}
