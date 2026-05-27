package com.recrutement.gestion_rh.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "job_offers")
@Data // Génère automatiquement les getters, setters, toString, equals
@NoArgsConstructor
@AllArgsConstructor
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements; // Compétences requises (Utile pour l'IA plus tard !)

    private String location; // Ville ou Télétravail

    private Double salary;

    private LocalDate postedDate;

    @Enumerated(EnumType.STRING)
    private JobStatus status; // Ex: OUVERTE, FERMEE

    @PrePersist
    protected void onCreate() {
        this.postedDate = LocalDate.now();
        if (this.status == null) {
            this.status = JobStatus.OUVERTE;
        }
    }
}