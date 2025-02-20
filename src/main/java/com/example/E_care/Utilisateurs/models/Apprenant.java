package com.example.E_care.Utilisateurs.models;

import jakarta.persistence.*;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("APPRENANT") // Indique que c'est un Apprenant dans la table User
public class Apprenant extends User {
    
    @Column(name = "localisation" , nullable = false)
    private String localisation;

    @Column(name = "sexe" , nullable = false)
    private String sexe;

    @Column(name = "date_naissance" , nullable = false)
    private String date_naissance;

    @Column(name = "divers" , nullable = true)
    private String divers;
}
