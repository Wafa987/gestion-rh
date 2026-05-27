package com.recrutement.gestion_rh.service;

import com.recrutement.gestion_rh.model.Application;
import com.recrutement.gestion_rh.model.ApplicationStatus;
import com.recrutement.gestion_rh.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    // Soumettre une candidature
    public Application submitApplication(Application application) {
        return applicationRepository.save(application);
    }

    // Récupérer les candidatures d'une offre précise (Besoin Recruteur)
    public List<Application> getApplicationsByOffer(Long offerId) {
        return applicationRepository.findByJobOfferId(offerId);
    }

    // Mettre à jour le statut et ajouter un commentaire (Sélection, Entretien, Décision)
    public Optional<Application> updateStatus(Long id, ApplicationStatus newStatus, String comment) {
        return applicationRepository.findById(id).map(app -> {
            app.setStatus(newStatus);
            app.setRecruiterComment(comment);
            return applicationRepository.save(app);
        });
    }
}