package com.example.E_care.Cours.ServicesImpl;

import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.models.Cours;
import com.example.E_care.media.models.Media;
import com.example.E_care.media.models.MediaType;
import com.example.E_care.Cours.dao.CategorieDao;
import com.example.E_care.media.dao.MediaDao;
import com.example.E_care.Cours.dto.CoursDto;
import com.example.E_care.media.dto.MediaDto;
import com.example.E_care.Cours.dao.CoursDao;
import com.example.E_care.Cours.Services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;




@Service(value = "coursService")
public class CoursImpl implements CoursService{

    
    @Autowired
    private CoursDao coursDao;

    @Autowired
    private CategorieDao categorieDao;

    @Autowired
    private MediaDao mediaDao;

    @Override
    public List<Cours> findAll() {
        return this.coursDao.findAll();
    }

    @Override
    public Cours save(CoursDto coursDto) {
        Optional<Categorie> categorieOpt = categorieDao.findById(coursDto.getCategorieId());
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie introuvable");
        }

        Cours cours = new Cours();
        cours.setTitre(coursDto.getTitre());
        cours.setContenu(coursDto.getContenu());
        cours.setCategorie(categorieOpt.get());

        // Sauvegarde du cours
        Cours savedCours = coursDao.save(cours);

        // Ajouter les médias liés
        List<Media> medias = new ArrayList<>();
        for (MediaDto mediaDto : coursDto.getMedias()) {
            Media media = new Media();
            media.setUrl(mediaDto.getUrl());
            media.setType(mediaDto.getType()); // Ajout du type
            media.setMediaType(MediaType.COURS);
            media.setCours(savedCours);
            medias.add(media);
        }

        // Sauvegarde des médias
        mediaDao.saveAll(medias);

        return savedCours;
    }

    @Override
    public Boolean deleteById (Long id) {
        Boolean result = false;
        try {
            this.coursDao.deleteById(id);
            result = true ;
        }catch(Exception e) {
            e.printStackTrace();
            
        }
        return result;
    }

    @Override
    public Cours update(Long id, CoursDto coursDto) {
        // Vérifier si la catégorie existe
        Optional<Categorie> categorieOpt = categorieDao.findById(coursDto.getCategorieId());
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie introuvable");
        }
    
        // Récupérer le cours existant
        Cours coursExistant = coursDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Cours introuvable"));
    
        // Mettre à jour les champs de base
        coursExistant.setTitre(coursDto.getTitre());
        coursExistant.setContenu(coursDto.getContenu());
        coursExistant.setCategorie(categorieOpt.get());
    
        // Mise à jour des médias
        if (coursDto.getMedias() != null) {
            // Supprimer les anciens médias proprement
            coursExistant.getMedias().clear();
    
            // Ajouter les nouveaux médias
            List<Media> medias = coursDto.getMedias().stream().map(mediaDto -> {
                Media media = new Media();
                media.setUrl(mediaDto.getUrl());
                media.setType(mediaDto.getType());
                media.setMediaType(MediaType.COURS);
                media.setCours(coursExistant);
                return media;
            }).collect(Collectors.toList());
    
            coursExistant.getMedias().addAll(medias);
        }
    
        // Sauvegarder l'entité mise à jour
        return coursDao.save(coursExistant);
    }
    

    @Override
    public List<Cours> findByCategorie(Categorie categorie){
       return
            this.coursDao.findByCategorie(categorie); 
    }
    
}

