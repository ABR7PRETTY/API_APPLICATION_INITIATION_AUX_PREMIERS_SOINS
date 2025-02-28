package com.example.E_care.Cours.ServicesImpl;


import com.example.E_care.Cours.models.Information;
import com.example.E_care.media.dao.MediaDao;
import com.example.E_care.media.dto.MediaDto;
import com.example.E_care.Cours.Services.InfoService;
import com.example.E_care.Cours.dto.InfoDto;
import com.example.E_care.Cours.dao.InfoDao;
import com.example.E_care.media.models.Media;
import com.example.E_care.media.models.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service(value = "infoService")
public class InfoImpl implements InfoService {

    @Autowired
    private InfoDao infoDao;


    @Autowired
    private MediaDao mediaDao;

    @Override
    public List<Information> findAll() {
        return this.infoDao.findAll();
    }

    @Override
    public Information save(InfoDto infoDto) {
        Information information = new Information();
        information.setTitre(infoDto.getTitre());
        information.setContenu(infoDto.getContenu());
        
        Information SavedInfo = this.infoDao.save(information);

        List<Media> medias = new ArrayList<>();
        for (MediaDto mediaDto : infoDto.getMedias()) {
            Media media = new Media();
            media.setUrl(mediaDto.getUrl());
            media.setType(mediaDto.getType());
            media.setMediaType(MediaType.INFORMATION);
            media.setInformations(SavedInfo);
            medias.add(media);
        }

        mediaDao.saveAll(medias);

        return SavedInfo;
    }

    @Override
    public Boolean deleteById(Long id) {
        Boolean result = false;
        if (this.infoDao.existsById(id)) {
            this.infoDao.deleteById(id);
            result = true;
        }
        return result;
    }

    @Override
    public Information update(Long id, InfoDto infoDto) {
        Information infoExistant = this.infoDao.findById(id).orElseThrow(() -> new RuntimeException("Information introuvable"));
        infoExistant.setTitre(infoDto.getTitre());
        infoExistant.setContenu(infoDto.getContenu());

        if(infoDto.getMedias() != null) {

            // Supprimer les anciens médias proprement
            infoExistant.getMedias().clear();

            List<Media> medias = new ArrayList<>();
            for (MediaDto mediaDto : infoDto.getMedias()) {
                Media media = new Media();
                media.setUrl(mediaDto.getUrl());
                media.setType(mediaDto.getType());
                media.setMediaType(MediaType.INFORMATION);
                media.setInformations(infoExistant);
                medias.add(media);
            }
            mediaDao.saveAll(medias);
        }
        return infoExistant;
    }
    
}
