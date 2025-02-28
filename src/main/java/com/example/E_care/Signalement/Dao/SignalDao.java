package com.example.E_care.Signalement.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.E_care.Signalement.models.Signal;

public interface SignalDao extends JpaRepository<Signal, Long> {
    
}
