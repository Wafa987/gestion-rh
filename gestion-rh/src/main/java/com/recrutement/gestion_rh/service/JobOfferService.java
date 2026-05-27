package com.recrutement.gestion_rh.service;

import com.recrutement.gestion_rh.model.JobOffer;
import com.recrutement.gestion_rh.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    // Créer ou modifier une offre
    public JobOffer saveOffer(JobOffer offer) {
        return jobOfferRepository.save(offer);
    }

    // Récupérer toutes les offres
    public List<JobOffer> getAllOffers() {
        return jobOfferRepository.findAll();
    }

    // Récupérer une offre par son ID
    public Optional<JobOffer> getOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    // Supprimer une offre
    public void deleteOffer(Long id) {
        jobOfferRepository.deleteById(id);
    }
}