package com.recrutement.gestion_rh.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "onboardings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le nouvel employé intégré (un User avec le rôle CANDIDAT à la base)
    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    // Le mentor assigné pour l'intégration (un autre User)
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private User mentor;

    private String currentStep; // Ex: "Signature contrat", "Matériel envoyé", "Complété"

    private boolean idDocumentProvided;
    private boolean vitaleCardProvided;
    private boolean ribProvided;

    @ElementCollection
    private List<String> checklistTasks; // Liste des tâches à accomplir (Ex: [ "Créer email pro", "Rendez-vous RH" ])
}