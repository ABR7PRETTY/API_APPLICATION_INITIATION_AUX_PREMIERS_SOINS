package com.example.E_care.Alerte.service;

import com.example.E_care.Alerte.dto.AlerteDto;
import com.example.E_care.Alerte.model.Alerte;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service(value = "alerteService")
public interface AlerteService {
    public List<Alerte> findAll();

    public Alerte save(AlerteDto alerteDto, List<MultipartFile> medias);

    public Boolean deleteById (Long id);

    public Alerte update(AlerteDto alerteDto, List<MultipartFile> files );

    public List<Alerte> findByUser(Long userId);

    public Long getTotalAlerte();

    
}