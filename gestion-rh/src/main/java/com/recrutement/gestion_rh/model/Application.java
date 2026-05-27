package com.recrutement.gestion_rh.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Plusieurs candidatures peuvent appartenir au même candidat (User)
    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    // Plusieurs candidatures peuvent postuler à la même offre (JobOffer)
    @ManyToOne
    @JoinColumn(name = "job_offer_id", nullable = false)
    private JobOffer jobOffer;

    private String cvUrl; // Lien vers le fichier du CV (ou texte du CV pour l'IA plus tard)

    @Column(columnDefinition = "TEXT")
    private String coverLetter; // Lettre de motivation

    private LocalDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status; // Suivi des étapes

    @Column(columnDefinition = "TEXT")
    private String recruiterComment; // Commentaires des recruteurs à chaque étape

    @PrePersist
    protected void onCreate() {
        this.applicationDate = LocalDateTime.now();
        if (this.status == null) {
            this.status = ApplicationStatus.RECU;
        }
    }
}