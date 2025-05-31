package com.example.E_care.Alerte.serviceImpl;

import com.example.E_care.Utilisateurs.models.User;
import com.example.E_care.Alerte.model.Alerte;
import com.example.E_care.media.models.Media;
import com.example.E_care.media.models.MediaType;
import com.example.E_care.Utilisateurs.dao.UserDao;
import com.example.E_care.media.dao.MediaDao;
import com.example.E_care.Alerte.dto.AlerteDto;
import com.example.E_care.Alerte.repository.AlerteRepository;
import com.example.E_care.Alerte.service.AlerteService;
import com.example.E_care.Alerte.config.AlerteSocketController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "alerteService")
public class AlerteImpl implements AlerteService {

    @Autowired
    private AlerteRepository alerteRepository;

    @Autowired
    private AlerteSocketController alerteSocketController;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MediaDao mediaDao;

    @Override
    public List<Alerte> findAll() {
        return this.alerteRepository.findActiveAlertes();
    }

    @Override
    public Alerte save(AlerteDto alerteDto, List<MultipartFile> mediasFiles) {
        Optional<User> userOpt = userDao.findById(alerteDto.getUserId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("utilisateur introuvable");
        }

        Alerte alerte = new Alerte();
        alerte.setTitre(alerteDto.getTitre());
        alerte.setContenu(alerteDto.getContenu());
        alerte.setLocalisation(alerteDto.getLocalisation());
        alerte.setLatitude(alerteDto.getLatitude());
        alerte.setLongitude(alerteDto.getLongitude());
        alerte.setUser(userOpt.get());
        alerte.setStatut(true);

        // Sauvegarde du alerte
        Alerte savedAlerte = alerteRepository.save(alerte);

        // Ajouter les médias liés
        if (mediasFiles == null || mediasFiles.isEmpty()) {
            return savedAlerte; // Pas de fichiers à traiter

        } else {
            List<Media> medias = new ArrayList<>();
            for (MultipartFile mediafile : mediasFiles) {
                if (mediafile.getSize() > 1048576) { // 1 Mo = 1048576 octets
                    throw new RuntimeException("Le fichier est trop volumineux (max 1 Mo)");
                    
                }
                Media media = new Media();
                try {
                    media.setFichier(mediafile.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException("Error reading media file", e);
                }
                media.setType(mediafile.getContentType()); // Ajout du type
                media.setMediaType(MediaType.ALERTE); // Spécifie le type de média
                media.setAlertes(savedAlerte);
                medias.add(media);
            }

            // Sauvegarde des médias
            mediaDao.saveAll(medias);
        }

        alerteSocketController.envoyerAlerte(savedAlerte);

        return savedAlerte;
    }

    @Override
    public Boolean deleteById(Long id) {
        Boolean result = false;
        try {
            this.alerteRepository.deleteById(id);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    public List<Alerte> findByUser(Long userId) {

        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User introuvable"));
        return this.alerteRepository.findByUser(user);
    }

    @Override
    public Long getTotalAlerte() {
        return alerteRepository.count();
    }

    @Override
    public Alerte update(AlerteDto alerteDto, List<MultipartFile> medias) {
        Optional<Alerte> optionalAlerte = alerteRepository.findById(alerteDto.getId());
        if (optionalAlerte.isPresent()) {
            Optional<User> Optionaluser = userDao.findById(alerteDto.getUserId());
            Alerte alerte = optionalAlerte.get();
            if (Optionaluser.isPresent()) {
                alerte.setUser(Optionaluser.get());
            }
            alerte.setTitre(alerteDto.getTitre());
            alerte.setContenu(alerteDto.getContenu());
            alerte.setLatitude(alerteDto.getLatitude());
            alerte.setLongitude(alerteDto.getLongitude());
            alerte.setLocalisation(alerteDto.getLocalisation());

            // Vérifie si une nouvelle image est envoyée
            if (!medias.isEmpty() || medias!= null ) {

                List<Media> files = new ArrayList<>();
            try {
                for (MultipartFile media : medias) {
                    Media file = new Media();
                    file.setAlertes(alerte);
                    file.setFichier(media.getBytes());
                    file.setType(media.getContentType());
                    file.setMediaType(MediaType.COURS);

                    files.add(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            alerte.getMedias().clear();
            alerte.getMedias().addAll(files);
                
            }

            return alerteRepository.save(alerte);
        } else {
            return null;
        }
    }

}
