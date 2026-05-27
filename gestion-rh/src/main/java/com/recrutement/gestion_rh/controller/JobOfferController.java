package com.recrutement.gestion_rh.controller;

import com.recrutement.gestion_rh.model.JobOffer;
import com.recrutement.gestion_rh.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "*") // Permet d'éviter les soucis de CORS si tu as un Front séparé
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    // Ajouter une offre
    @PostMapping
    public JobOffer createOffer(@RequestBody JobOffer offer) {
        return jobOfferService.saveOffer(offer);
    }

    // Lire toutes les offres
    @GetMapping
    public List<JobOffer> getAllOffers() {
        return jobOfferService.getAllOffers();
    }

    // Lire une offre précise
    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getOfferById(@PathVariable Long id) {
        return jobOfferService.getOfferById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Modifier une offre
    @PutMapping("/{id}")
    public ResponseEntity<JobOffer> updateOffer(@PathVariable Long id, @RequestBody JobOffer offerDetails) {
        return jobOfferService.getOfferById(id).map(existingOffer -> {
            existingOffer.setTitle(offerDetails.getTitle());
            existingOffer.setDescription(offerDetails.getDescription());
            existingOffer.setRequirements(offerDetails.getRequirements());
            existingOffer.setLocation(offerDetails.getLocation());
            existingOffer.setSalary(offerDetails.getSalary());
            existingOffer.setStatus(offerDetails.getStatus());
            JobOffer updatedOffer = jobOfferService.saveOffer(existingOffer);
            return ResponseEntity.ok(updatedOffer);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une offre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        if (jobOfferService.getOfferById(id).isPresent()) {
            jobOfferService.deleteOffer(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}