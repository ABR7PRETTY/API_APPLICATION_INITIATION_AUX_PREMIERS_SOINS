package com.example.E_care.Cours.models;


import java.util.List;
import com.example.E_care.media.models.MediaInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity // Héritage dans une seule table
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

    @OneToMany(mappedBy = "info", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaInfo> medias;


}