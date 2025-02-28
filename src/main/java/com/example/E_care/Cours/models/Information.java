package com.example.E_care.Cours.models;


import java.util.List;
import com.example.E_care.media.models.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "Information")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre", nullable = false, unique = true)
    private String titre;

    @Column(name = "contenu", nullable = false)
    private String contenu;

    @OneToMany(mappedBy = "informations", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> medias;


}