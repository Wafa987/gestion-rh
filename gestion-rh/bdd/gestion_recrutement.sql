-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 27 mai 2026 à 19:55
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_recrutement`
--

-- --------------------------------------------------------

--
-- Structure de la table `applications`
--

DROP TABLE IF EXISTS `applications`;
CREATE TABLE IF NOT EXISTS `applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `application_date` datetime(6) DEFAULT NULL,
  `cover_letter` text,
  `cv_url` varchar(255) DEFAULT NULL,
  `recruiter_comment` text,
  `status` enum('ACCEPTE','ENTRETIEN','EN_COURS','RECU','REJETE') DEFAULT NULL,
  `candidate_id` bigint NOT NULL,
  `job_offer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32iahkg1fqci0pt76uql80nj7` (`candidate_id`),
  KEY `FKawuu01ou28l0903avu0nwx058` (`job_offer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `applications`
--

INSERT INTO `applications` (`id`, `application_date`, `cover_letter`, `cv_url`, `recruiter_comment`, `status`, `candidate_id`, `job_offer_id`) VALUES
(30, '2026-05-27 19:00:21.024271', 'Candidature au poste d’Alternante Chef de Projet - Data Analyst.pdf', 'cv&.pdf', '[📅 ENTRETIEN LE 2026-05-13 à 21:39] Dossier en cours de traitement.', 'EN_COURS', 4, 1),
(31, '2026-05-27 19:14:58.312391', 'cv1.pdf', 'cv&.pdf', NULL, 'RECU', 2, 1),
(32, '2026-05-27 19:17:43.137646', 'CV.pdf', 'CV.pdf', '', 'EN_COURS', 2, 1),
(33, '2026-05-27 19:21:42.841855', 'lm_5_1_1779909702720.pdf', 'cv_5_1_1779909702718.pdf', NULL, 'RECU', 5, 1),
(34, '2026-05-27 19:36:37.033517', 'lm_4_1_1779910596942.pdf', 'cv_4_1_1779910596940.pdf', '', 'EN_COURS', 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `job_offers`
--

DROP TABLE IF EXISTS `job_offers`;
CREATE TABLE IF NOT EXISTS `job_offers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `posted_date` date DEFAULT NULL,
  `requirements` text,
  `salary` double DEFAULT NULL,
  `status` enum('FERMEE','OUVERTE') DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `job_offers`
--

INSERT INTO `job_offers` (`id`, `description`, `location`, `posted_date`, `requirements`, `salary`, `status`, `title`) VALUES
(1, 'Nous recherchons un(e) étudiant(e) en M2 MIAGE pour booster nos applications back-end.', 'Mulhouse', '2026-05-27', 'Java 17, Spring Boot, Hibernate, MySQL, Swagger', 42000, 'OUVERTE', 'Développeur Java Spring Boot'),
(2, 'Accompagnement de 2 équipes de développement. Maîtrise des frameworks Scrum et Kanban indispensable', 'Paris', '2026-05-27', NULL, 37000, 'OUVERTE', 'Scrum Master / Chef de Projet Agile');

-- --------------------------------------------------------

--
-- Structure de la table `onboardings`
--

DROP TABLE IF EXISTS `onboardings`;
CREATE TABLE IF NOT EXISTS `onboardings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `current_step` varchar(255) DEFAULT NULL,
  `id_document_provided` bit(1) NOT NULL,
  `rib_provided` bit(1) NOT NULL,
  `vitale_card_provided` bit(1) NOT NULL,
  `employee_id` bigint NOT NULL,
  `mentor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhlw7i9htsfrrjmiebrd3cdihr` (`employee_id`),
  KEY `FKm2d31i0r340j2qf5j6hqy4ehb` (`mentor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `onboardings`
--

INSERT INTO `onboardings` (`id`, `current_step`, `id_document_provided`, `rib_provided`, `vitale_card_provided`, `employee_id`, `mentor_id`) VALUES
(1, 'validation', b'1', b'1', b'1', 1, NULL),
(2, 'Initialisation', b'0', b'0', b'0', 3, NULL),
(3, 'Initialisation', b'0', b'0', b'0', 4, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `onboarding_checklist_tasks`
--

DROP TABLE IF EXISTS `onboarding_checklist_tasks`;
CREATE TABLE IF NOT EXISTS `onboarding_checklist_tasks` (
  `onboarding_id` bigint NOT NULL,
  `checklist_tasks` varchar(255) DEFAULT NULL,
  KEY `FKkcw1are37xepkrdmktbamh7jy` (`onboarding_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN_RH','CANDIDAT','RECRUTEUR') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`, `role`) VALUES
(1, 'wafa@gmail.com', 'wafa', 'melissa', '123', 'CANDIDAT'),
(2, 'wafazait17@gmail.com', 'Nouveau', 'Candidat', '1234567', 'CANDIDAT'),
(3, 'mm@gmail.com', 'Nouveau', 'Candidat', '7676', 'CANDIDAT'),
(4, 'wafazait@gmail.com', 'Wafa', 'ZAIT', '1234', 'CANDIDAT'),
(5, 'mel@gmail.com', 'mel', 'MEL', '12', 'CANDIDAT');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `applications`
--
ALTER TABLE `applications`
  ADD CONSTRAINT `FK32iahkg1fqci0pt76uql80nj7` FOREIGN KEY (`candidate_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKawuu01ou28l0903avu0nwx058` FOREIGN KEY (`job_offer_id`) REFERENCES `job_offers` (`id`);

--
-- Contraintes pour la table `onboardings`
--
ALTER TABLE `onboardings`
  ADD CONSTRAINT `FKgyvyclbpboswa71t2fmm6d4so` FOREIGN KEY (`employee_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKm2d31i0r340j2qf5j6hqy4ehb` FOREIGN KEY (`mentor_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `onboarding_checklist_tasks`
--
ALTER TABLE `onboarding_checklist_tasks`
  ADD CONSTRAINT `FKkcw1are37xepkrdmktbamh7jy` FOREIGN KEY (`onboarding_id`) REFERENCES `onboardings` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
