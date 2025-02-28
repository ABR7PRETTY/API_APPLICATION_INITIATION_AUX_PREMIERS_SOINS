package com.example.E_care.Cours.Services;

import com.example.E_care.Cours.dto.InfoDto;
import com.example.E_care.Cours.models.Information;
import org.springframework.stereotype.Service;
import java.util.List;

@Service(value = "infoService")
public interface InfoService {
    public List<Information> findAll();

    public Information save(InfoDto infoDto);

    public Boolean deleteById (Long id);

    public Information update(Long id, InfoDto infoDto);

    
}