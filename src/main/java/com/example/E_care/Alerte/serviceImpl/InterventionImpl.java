package com.example.E_care.Alerte.serviceImpl;

import com.example.E_care.Utilisateurs.models.Administrateur;
import com.example.E_care.Utilisateurs.models.Apprenant;
import com.example.E_care.Utilisateurs.models.User;
import com.example.E_care.Alerte.config.AlerteSocketController;
import com.example.E_care.Alerte.dto.InterventionDto;
import com.example.E_care.Alerte.model.Alerte;
import com.example.E_care.Alerte.model.Intervention;
import com.example.E_care.Alerte.model.Statut;
import com.example.E_care.Alerte.repository.AlerteRepository;
import com.example.E_care.Alerte.repository.InterventionRepository;
import com.example.E_care.Alerte.service.InterventionService;
import com.example.E_care.Urgence.dao.HopitalDao;
import com.example.E_care.Urgence.models.Hopital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Service(value = "interventionService")
public class InterventionImpl implements InterventionService{

    
    @Autowired
    private InterventionRepository interventionRepository;

    @Autowired
    private AlerteSocketController alerteSocketController;

    @Autowired
    private AlerteRepository alerteRepository;

    @Autowired
    private HopitalDao hopitalRepository;


    @Override
    public List<InterventionDto> findAllByAdmin(User user) {
        Administrateur admin = (Administrateur) user;
        Hopital hopital = admin.getHopital();
        List<Intervention> interventions = this.interventionRepository.findByHopital(hopital);
        List<InterventionDto> interventionDtos = new ArrayList<>();
        for (Intervention intervention : interventions) {
            InterventionDto dto = new InterventionDto();
            dto.setId(intervention.getId());
            dto.setAlerteId(intervention.getAlerte().getId());
            dto.setStatutIntervention(intervention.getStatut());
            dto.setDate(intervention.getDate());
            dto.setTitre(intervention.getAlerte().getTitre());
            dto.setContenu(intervention.getAlerte().getContenu());
            dto.setLocalisation(intervention.getAlerte().getLocalisation());
            dto.setUserId(intervention.getAlerte().getUser().getId());
            dto.setHopital(hopital.getNom());
            interventionDtos.add(dto);
        }

        return interventionDtos;
    }

    @Override
    @Transactional
    public List<Intervention> findAll() {
        return this.interventionRepository.findAll();
    }

    @Override
    public List<Intervention> findAllByUser(User user) {
        Apprenant apprenant= (Apprenant) user;
        return this.interventionRepository.findInterventionsByUser(apprenant);
    }


    @Override
    public Intervention accepterIntervention(Long id, User user) {
        Optional<Alerte> optionalAlerte = alerteRepository.findById(id);
        Administrateur admin = (Administrateur) user;
        if (optionalAlerte.isPresent()) {
            Alerte alerte = optionalAlerte.get();
            Optional<Intervention> optionalIntervention = interventionRepository.findByAlerte(alerte);
            if (optionalIntervention.isPresent()) {
                interventionRepository.delete(optionalIntervention.get());
            }
            Intervention intervention = new Intervention();
            try {
                intervention.setAlerte(alerte);
                intervention.setStatut(Statut.En_traitement);
                intervention.setHopital(admin.getHopital());
                alerte.setStatut(false);
                alerteRepository.save(alerte);
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
            Intervention savedIntervention = interventionRepository.save(intervention);
            // Send the intervention to the WebSocket topic
            alerteSocketController.accepterAlerte(savedIntervention);
            return savedIntervention;
        } else {
            return null;
        }
    }

    @Override
    public Intervention interompreIntervention(Long id) {
        Optional<Intervention> optionalIntervention = interventionRepository.findById(id);
        if (optionalIntervention.isPresent()) {
            Intervention intervention = optionalIntervention.get();
            try {
                intervention.setStatut(Statut.Interompu);
                Alerte alerte = intervention.getAlerte();
                alerte.setStatut(true);
                alerteRepository.save(alerte);
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
            
            Intervention pausedIntervention = interventionRepository.save(intervention);
            alerteSocketController.interompreAlerte(pausedIntervention);
            return pausedIntervention;
        } else {
            return null;
        }
    }

    @Override
    public Intervention reprendreIntervention(Long id) {
        Optional<Intervention> optionalIntervention = interventionRepository.findById(id);
        if (optionalIntervention.isPresent()) {
            Intervention intervention = optionalIntervention.get();
            try {
                intervention.setStatut(Statut.En_traitement);
                Alerte alerte = intervention.getAlerte();
                alerte.setStatut(false);
                alerteRepository.save(alerte);
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
            Intervention playedIntervention = interventionRepository.save(intervention);
            alerteSocketController.reprendreAlerte(playedIntervention);
            return playedIntervention;
        } else {
            return null;
        }
    }

    @Override
    public Intervention acheverIntervention(Long id) {
        Optional<Intervention> optionalIntervention = interventionRepository.findById(id);
        if (optionalIntervention.isPresent()) {
            Intervention intervention = optionalIntervention.get();
            try {
                intervention.setStatut(Statut.Achevé);
                Alerte alerte = intervention.getAlerte();
                alerte.setStatut(false);
                alerteRepository.save(alerte);
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
            Intervention finishedIntervention = interventionRepository.save(intervention);
            alerteSocketController.terminerAlerte(finishedIntervention);
            return finishedIntervention;
        } else {
            return null;
        }
    }

    
    @Override
    public Boolean deleteById (Long id) {
        Boolean result = false;
        try {
            this.interventionRepository.deleteById(id);
            result = true ;
        }catch(Exception e) {
            e.printStackTrace();
            
        }
        return result;
    }
    

    
        public List<Intervention> findByHopital(Long hopitalId){
    
            Hopital hopital = hopitalRepository.findById(hopitalId)
                .orElseThrow(() -> new RuntimeException("hopital introuvable"));
                return
                this.interventionRepository.findByHopital(hopital);
            }

    @Override
    public Long getTotalIntervention() {
         return interventionRepository.count();
    }

    
        
    }
    







