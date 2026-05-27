package com.recrutement.gestion_rh.repository;

import com.recrutement.gestion_rh.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    // Grâce à JpaRepository, on a déjà gratuitement : save(), findById(), findAll(), deleteById()
}