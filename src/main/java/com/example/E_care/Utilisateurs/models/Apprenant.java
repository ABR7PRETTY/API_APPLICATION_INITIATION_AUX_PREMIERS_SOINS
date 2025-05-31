package com.example.E_care.Utilisateurs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("APPRENANT") // Indique que c'est un Apprenant dans la table User
public class Apprenant extends User {

    @Column(name = "telephone", nullable = true)
    private String telephone;
    
    @Column(name = "localisation" , nullable = true)
    private String localisation;

    @Column(name = "sexe" , nullable = true)
    private String sexe;

    @Column(name = "date_naissance" , nullable = true)
    private String date_naissance;

    @Column(name = "divers" , nullable = true)
    private String divers;
}
