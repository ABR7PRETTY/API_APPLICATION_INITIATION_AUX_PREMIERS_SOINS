package com.example.E_care.Urgence.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "NumerosVerts")
public class NumerosVerts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "description", nullable = true)
    private String description;
}