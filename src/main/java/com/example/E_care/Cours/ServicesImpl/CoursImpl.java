package com.example.E_care.Cours.ServicesImpl;

import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.models.Cours;
import com.example.E_care.media.models.Media;
import com.example.E_care.media.models.MediaType;
import com.example.E_care.Cours.dao.CategorieDao;
import com.example.E_care.media.dao.MediaDao;
import com.example.E_care.Cours.dto.CoursDto;
import com.example.E_care.Cours.dao.CoursDao;
import com.example.E_care.Cours.Services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




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
    public Cours save(CoursDto coursDto, List<MultipartFile> mediasFiles) {
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
        // Vérifie si des fichiers sont envoyés
        if (mediasFiles == null || mediasFiles.isEmpty()) {
            return savedCours; // Pas de fichiers à traiter
        }else {
            // Traite les fichiers envoyés
            List<Media> medias = new ArrayList<>();
            for (MultipartFile mediafile : mediasFiles) {
                Media media = new Media();
                try {
                    media.setFichier(mediafile.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException("Error reading media file", e);
                }
                media.setType(mediafile.getContentType()); // Ajout du type
                media.setMediaType(MediaType.COURS);
                media.setCours(savedCours);
                medias.add(media);
            }
    
            // Sauvegarde des médias
            mediaDao.saveAll(medias);
        }
        

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
    public List<Cours> findByCategorie(Long categorieId){

        Categorie categorie = categorieDao.findById(categorieId)
            .orElseThrow(() -> new RuntimeException("Categorie introuvable"));
            return
            this.coursDao.findByCategorie(categorie);
        }

    @Override
    public Long getTotalCours() {
         return coursDao.count();
    }

    @Override
    public Cours update(Long id, String titre, String contenu, Long categorieId, List<MultipartFile> medias){
        Optional<Cours> optionalCours = coursDao.findById(id);
        if (optionalCours.isPresent()) {
            Optional<Categorie> Optionalcategorie = categorieDao.findById(categorieId);
            Cours cours = optionalCours.get();
            if (Optionalcategorie.isPresent()) {
                cours.setCategorie(Optionalcategorie.get());
                }
            cours.setTitre(titre);
            cours.setContenu(contenu);

            // Vérifie si une nouvelle image est envoyée
            if (medias == null || medias.isEmpty()) {
                return coursDao.save(cours); // Pas de fichiers à traiter
            } else {

                List<Media> files = new ArrayList<>();
                try {
                    for(MultipartFile media : medias){
                        Media file = new Media();
                        file.setCours(cours);
                        file.setFichier(media.getBytes());
                        file.setType(media.getContentType());
                        file.setMediaType(MediaType.COURS);

                        files.add(file);
                     }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                    cours.getMedias().clear();
                    cours.getMedias().addAll(files);

            return coursDao.save(cours);
        }}
        else {
            return null;
    }
}
        
    }
    

