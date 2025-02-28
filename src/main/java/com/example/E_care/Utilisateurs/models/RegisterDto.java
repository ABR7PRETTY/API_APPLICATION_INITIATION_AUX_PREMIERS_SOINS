package com.example.E_care.Utilisateurs.models;

import com.example.E_care.Urgence.models.Hopital;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class RegisterDto {
    private String username;
    private String nom;
    private String prenom;
    private String telephone;
    private String profil;
    private String password;
    private Role role;
    private String localisation;
    private String sexe;
    private String date_naissance;
    private String divers;
    private Hopital hopital;
}
