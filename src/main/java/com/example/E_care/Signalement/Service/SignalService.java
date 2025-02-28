package com.example.E_care.Signalement.Service;


import java.util.List;
import org.springframework.stereotype.Service;
import com.example.E_care.Signalement.models.Signal;


@Service(value = "signalService")
public interface SignalService {


    public Signal save(Signal signal);

    public Signal update(Long id, Signal signal);

    public Boolean deleteById(Long id);

    public List<Signal> findAll();

    
} 