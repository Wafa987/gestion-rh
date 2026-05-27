package com.recrutement.gestion_rh.repository;

import com.recrutement.gestion_rh.model.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
    Optional<Onboarding> findByEmployeeId(Long employeeId);
}