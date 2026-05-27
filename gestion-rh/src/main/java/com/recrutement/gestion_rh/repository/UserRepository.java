package com.recrutement.gestion_rh.repository;

import com.recrutement.gestion_rh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Cette méthode nous sera indispensable pour l'authentification et l'IA
    Optional<User> findByEmail(String email);
}