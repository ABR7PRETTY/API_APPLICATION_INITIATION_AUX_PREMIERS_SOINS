package com.example.E_care.Cours.Services;

import com.example.E_care.Cours.dto.InfoDto;
import com.example.E_care.Cours.models.Information;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service(value = "infoService")
public interface InfoService {
    
    public Information save(InfoDto infoDto, List<MultipartFile> medias);

    public Boolean deleteById (Long id);

    public Information update(Long id, String titre, String contenu, LocalDate date_fin, List<MultipartFile> files );

    public List<Information> findInformation();

    public Long getTotalInformation();

    @Scheduled(cron = "0 0 0 * * ?") // Exécution chaque jour à minuit
    public void supprimerInformationsExpirees();

    
}