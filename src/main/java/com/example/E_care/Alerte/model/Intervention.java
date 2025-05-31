package com.example.E_care.Alerte.model;


import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.example.E_care.Urgence.models.Hopital;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "Intervention")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "alerte_id", nullable = false)
    private Alerte alerte;


    @Column(name = "date_intervention", nullable = true)
    @CreationTimestamp
    private Date date;

    @Column(name = "statut", nullable = false)
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "hopital_id", nullable = true)
    private Hopital hopital;


}