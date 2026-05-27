package com.recrutement.gestion_rh.repository;

import com.recrutement.gestion_rh.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // Permet au recruteur de voir toutes les candidatures pour une offre spécifique
    List<Application> findByJobOfferId(Long jobOfferId);

    // Permet à un candidat de voir l'historique de ses propres candidatures
    List<Application> findByCandidateId(Long candidateId);
}