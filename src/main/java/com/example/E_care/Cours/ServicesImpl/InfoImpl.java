package com.example.E_care.Cours.ServicesImpl;


import com.example.E_care.Cours.models.Information;
import com.example.E_care.media.dao.MediaDao;
import com.example.E_care.Cours.Services.InfoService;
import com.example.E_care.Cours.dto.InfoDto;
import com.example.E_care.Cours.dao.InfoDao;
import com.example.E_care.media.models.Media;
import com.example.E_care.media.models.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Service(value = "infoService")
public class InfoImpl implements InfoService {

    @Autowired
    private InfoDao infoDao;


    @Autowired
    private MediaDao mediaDao;

    @Override
    public Information save(InfoDto infoDto, List<MultipartFile> mediasFiles) {
        
        Information Information = new Information();
        Information.setTitre(infoDto.getTitre());
        Information.setContenu(infoDto.getContenu());
        Information.setDateFin(infoDto.getDate_fin());

        // Sauvegarde du Information
        Information savedInformation = infoDao.save(Information);

        // Ajouter les médias liés
        // Vérifie si des fichiers sont envoyés
        if (mediasFiles == null || mediasFiles.isEmpty()) {
            return savedInformation; // Pas de fichiers à traiter
        }
        List<Media> medias = new ArrayList<>();
        for (MultipartFile mediafile : mediasFiles) {
            Media media = new Media();
            try {
                media.setFichier(mediafile.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Error reading media file", e);
            }
            media.setType(mediafile.getContentType()); // Ajout du type
            media.setMediaType(MediaType.INFORMATION);
            media.setInformations(savedInformation);
            medias.add(media);
        }

        // Sauvegarde des médias
        mediaDao.saveAll(medias);

        return savedInformation;
    }

    @Override
    public Boolean deleteById (Long id) {
        Boolean result = false;
        try {
            this.infoDao.deleteById(id);
            result = true ;
        }catch(Exception e) {
            e.printStackTrace();
            
        }
        return result;
    }
    
    @Override
    public List<Information> findInformation(){
        return this.infoDao.findAll();
    }

    @Override
    public Long getTotalInformation() {
         return infoDao.count();
    }


    @Override
    public void supprimerInformationsExpirees() {
        Date now = new Date();
        infoDao.deleteByDateFinBefore(now);
    }

    @Override
    public Information update(Long id, String titre, String contenu, LocalDate date_fin, List<MultipartFile> medias){
        Optional<Information> optionalInformation = infoDao.findById(id);
        if (optionalInformation.isPresent()) {
            Information Information = optionalInformation.get();
            Information.setTitre(titre);
            Information.setContenu(contenu);
            Information.setDateFin(date_fin);

            // Vérifie si une nouvelle image est envoyée
            if (medias == null || medias.isEmpty()) {
                return infoDao.save(Information); // Pas de fichiers à traiter
            }

                List<Media> files = new ArrayList<>();
                try {
                    for(MultipartFile media : medias){
                        Media file = new Media();
                        file.setInformations(Information);
                        file.setFichier(media.getBytes());
                        file.setType(media.getContentType());
                        file.setMediaType(MediaType.INFORMATION);

                        files.add(file);
                     }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                    Information.getMedias().clear();
                    Information.getMedias().addAll(files);

            return infoDao.save(Information);
        }
        else {
            return null;
    }
}
    
}
