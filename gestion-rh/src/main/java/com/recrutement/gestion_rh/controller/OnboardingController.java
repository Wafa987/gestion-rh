package com.recrutement.gestion_rh.controller;

import com.recrutement.gestion_rh.model.Onboarding;
import com.recrutement.gestion_rh.model.User;
import com.recrutement.gestion_rh.service.OnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/onboardings")
@CrossOrigin(origins = "*")
public class OnboardingController {

    @Autowired
    private OnboardingService onboardingService;

    @PostMapping
    public Onboarding startOnboarding(@RequestBody Onboarding onboarding) {
        return onboardingService.createOnboarding(onboarding);
    }

    @GetMapping
    public List<Onboarding> getAll() {
        return onboardingService.getAllOnboardings();
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<Onboarding> updateProgress(
            @PathVariable Long id,
            @RequestParam String currentStep,
            @RequestParam boolean idDoc,
            @RequestParam boolean vitaleDoc,
            @RequestParam boolean ribDoc) {
        return onboardingService.updateProgress(id, currentStep, idDoc, vitaleDoc, ribDoc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}