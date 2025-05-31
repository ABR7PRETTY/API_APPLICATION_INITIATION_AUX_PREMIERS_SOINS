package com.example.E_care.Cours.dao;

import com.example.E_care.Cours.models.Information;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoDao extends JpaRepository <Information, Long> {

    void deleteByDateFinBefore(Date now);
    
}
