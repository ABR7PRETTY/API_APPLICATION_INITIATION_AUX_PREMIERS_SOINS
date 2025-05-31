package com.example.E_care.Alerte.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.E_care.Alerte.model.Alerte;
import com.example.E_care.Alerte.model.Intervention;

@Controller
public class AlerteSocketController {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public void envoyerAlerte(Alerte alerte) {
    messagingTemplate.convertAndSend("/topic/alertes", alerte);
    
  }

  public void accepterAlerte(Intervention intervention) {
    String message = "L'alerte \"" + intervention.getAlerte().getTitre() + 
                    "\" a été acceptée par l'hôpital \"" + intervention.getHopital().getNom() + "\".";
    
    // Utilisez "/queue" au lieu de "/topic" pour les messages privés
    messagingTemplate.convertAndSendToUser(intervention.getAlerte().getUser().getUsername(), "/queue/interventions", message);
    System.out.println("Message envoyé à l'utilisateur " + intervention.getAlerte().getUser().getUsername() + ": " + message);
}

  
    public void interompreAlerte(Intervention intervention) {
      String message = "L'hôpital \"" + intervention.getHopital().getNom() + 
                      "\" a interrompue l'intervention sur l'alerte \"" + intervention.getAlerte().getTitre() + "\".";
      
      // Utilisez "/queue" au lieu de "/topic" pour les messages privés
      messagingTemplate.convertAndSendToUser(intervention.getAlerte().getUser().getUsername(), "/queue/interventions", message);
      System.out.println("Message envoyé à l'utilisateur " + intervention.getAlerte().getUser().getUsername() + ": " + message);
    }


    public void reprendreAlerte(Intervention intervention) {
      String message = "L'hôpital \"" + intervention.getHopital().getNom() + 
                      "\" a repris l'intervention sur l'alerte \"" + intervention.getAlerte().getTitre() + "\".";
      
      // Utilisez "/queue" au lieu de "/topic" pour les messages privés
      messagingTemplate.convertAndSendToUser(intervention.getAlerte().getUser().getUsername(), "/queue/interventions", message);
      System.out.println("Message envoyé à l'utilisateur " + intervention.getAlerte().getUser().getUsername() + ": " + message);
    }


    public void terminerAlerte(Intervention intervention) {
      String message = "L'hôpital \"" + intervention.getHopital().getNom() + 
                      "\" a terminé l'intervention sur l'alerte \"" + intervention.getAlerte().getTitre() + "\".";
      
      // Utilisez "/queue" au lieu de "/topic" pour les messages privés
      messagingTemplate.convertAndSendToUser(intervention.getAlerte().getUser().getUsername(), "/queue/interventions", message);
      System.out.println("Message envoyé à l'utilisateur " + intervention.getAlerte().getUser().getUsername()+ ": " + message);
    }

}

