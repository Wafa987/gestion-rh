package com.recrutement.gestion_rh.service;

import com.recrutement.gestion_rh.model.Onboarding;
import com.recrutement.gestion_rh.model.User;
import com.recrutement.gestion_rh.repository.OnboardingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OnboardingService {

    @Autowired
    private OnboardingRepository onboardingRepository;

    // Initialiser un parcours d'onboarding
    public Onboarding createOnboarding(Onboarding onboarding) {
        onboarding.setCurrentStep("Initialisation");
        return onboardingRepository.save(onboarding);
    }

    // Récupérer tous les Onboardings en cours (pour les RH)
    public List<Onboarding> getAllOnboardings() {
        return onboardingRepository.findAll();
    }

    // Mettre à jour les documents reçus et l'étape actuelle
    public Optional<Onboarding> updateProgress(Long id, String step, boolean idDoc, boolean vitaleDoc, boolean ribDoc) {
        return onboardingRepository.findById(id).map(o -> {
            o.setCurrentStep(step);
            o.setIdDocumentProvided(idDoc);
            o.setVitaleCardProvided(vitaleDoc);
            o.setRibProvided(ribDoc);
            return onboardingRepository.save(o);
        });
    }

    // Assigner un mentor
    public Optional<Onboarding> assignMentor(Long id, User mentor) {
        return onboardingRepository.findById(id).map(o -> {
            o.setMentor(mentor);
            return onboardingRepository.save(o);
        });
    }
}