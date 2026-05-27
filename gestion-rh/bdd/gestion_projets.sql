-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 27 mai 2026 à 19:54
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
-- Base de données : `gestion_projets`
--

-- --------------------------------------------------------

--
-- Structure de la table `departement`
--

DROP TABLE IF EXISTS `departement`;
CREATE TABLE IF NOT EXISTS `departement` (
  `deptEmp` varchar(20) NOT NULL,
  `mgrEmp` varchar(100) NOT NULL,
  PRIMARY KEY (`deptEmp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `departement`
--

INSERT INTO `departement` (`deptEmp`, `mgrEmp`) VALUES
('10', 'Holmes'),
('12', 'Lupin');

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

DROP TABLE IF EXISTS `employe`;
CREATE TABLE IF NOT EXISTS `employe` (
  `idEmp` varchar(10) NOT NULL,
  `nomEmp` varchar(100) NOT NULL,
  `salEmp` decimal(10,0) NOT NULL,
  `deptEmp` varchar(20) NOT NULL,
  `login` varchar(80) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `is_manager` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idEmp`),
  UNIQUE KEY `login` (`login`),
  KEY `fk_emp_dept` (`deptEmp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`idEmp`, `nomEmp`, `salEmp`, `deptEmp`, `login`, `password_hash`, `is_manager`) VALUES
('E101', 'Durand', 45000, '10', NULL, NULL, 0),
('E105', 'Adam', 43000, '12', NULL, NULL, 0),
('E110', 'Rivera', 41000, '10', NULL, NULL, 0),
('E120', 'Martin', 46000, '10', NULL, NULL, 0),
('E121', 'Dubois', 42000, '12', NULL, NULL, 0),
('E122', 'Nguyen', 48000, '10', NULL, NULL, 0),
('E123', 'Lopez', 44000, '12', NULL, NULL, 0),
('E124', 'Benali', 47000, '10', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `projet`
--

DROP TABLE IF EXISTS `projet`;
CREATE TABLE IF NOT EXISTS `projet` (
  `nomProj` varchar(50) NOT NULL,
  `mgrProj` varchar(100) NOT NULL,
  `budget` decimal(12,0) NOT NULL,
  `dateDebut` date NOT NULL,
  PRIMARY KEY (`nomProj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `projet`
--

INSERT INTO `projet` (`nomProj`, `mgrProj`, `budget`, `dateDebut`) VALUES
('ILO', 'Dupont', 100000, '2011-11-15'),
('MAXI', 'Jones', 200000, '2012-01-03');

-- --------------------------------------------------------

--
-- Structure de la table `travail`
--

DROP TABLE IF EXISTS `travail`;
CREATE TABLE IF NOT EXISTS `travail` (
  `nomProj` varchar(50) NOT NULL,
  `idEmp` varchar(10) NOT NULL,
  `heures` int NOT NULL,
  `evalEmp` tinyint DEFAULT NULL,
  PRIMARY KEY (`nomProj`,`idEmp`),
  KEY `fk_trav_emp` (`idEmp`)
) ;

--
-- Déchargement des données de la table `travail`
--

INSERT INTO `travail` (`nomProj`, `idEmp`, `heures`, `evalEmp`) VALUES
('ILO', 'E101', 0, NULL),
('ILO', 'E105', 35, 5),
('ILO', 'E110', 10, 8),
('ILO', 'E123', 20, 10),
('MAXI', 'E101', 10, NULL);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `employe`
--
ALTER TABLE `employe`
  ADD CONSTRAINT `fk_emp_dept` FOREIGN KEY (`deptEmp`) REFERENCES `departement` (`deptEmp`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Contraintes pour la table `travail`
--
ALTER TABLE `travail`
  ADD CONSTRAINT `fk_trav_emp` FOREIGN KEY (`idEmp`) REFERENCES `employe` (`idEmp`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_trav_proj` FOREIGN KEY (`nomProj`) REFERENCES `projet` (`nomProj`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
