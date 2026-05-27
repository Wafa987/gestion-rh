package com.recrutement.gestion_rh.controller;

import com.recrutement.gestion_rh.model.Application;
import com.recrutement.gestion_rh.model.ApplicationStatus;
import com.recrutement.gestion_rh.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Postuler à une offre
    @PostMapping
    public Application apply(@RequestBody Application application) {
        return applicationService.submitApplication(application);
    }

    // Afficher les candidatures reçues pour une offre spécifique
    @GetMapping("/offer/{offerId}")
    public List<Application> getByOffer(@PathVariable Long offerId) {
        return applicationService.getApplicationsByOffer(offerId);
    }

    // Changer le statut d'une candidature + Ajouter un commentaire
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> changeStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status,
            @RequestParam(required = false) String comment) {

        return applicationService.updateStatus(id, status, comment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}