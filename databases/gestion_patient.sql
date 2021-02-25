-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 25 fév. 2021 à 08:33
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_patient`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie_medecins`
--

CREATE TABLE `categorie_medecins` (
  `id` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1:Generaliste/2=Specialiste/3=Professeur',
  `tarif` float NOT NULL COMMENT '1:15.000Ar/2:20.000Ar/3:6.000Ar'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `consultations`
--

CREATE TABLE `consultations` (
  `id` int(11) NOT NULL,
  `medecin_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `dateConsultation` datetime NOT NULL DEFAULT current_timestamp(),
  `observations` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `consultations`
--

INSERT INTO `consultations` (`id`, `medecin_id`, `patient_id`, `dateConsultation`, `observations`) VALUES
(3, 1, 1, '2021-02-24 15:30:35', 'Papillon aux ventres'),
(4, 2, 1, '2021-02-24 15:35:21', 'Elle est constipée'),
(5, 4, 4, '2021-02-24 16:04:56', 'Maladie damour'),
(6, 1, 1, '2021-02-24 16:13:51', 'Regles douloureuses'),
(7, 1, 1, '2021-02-24 16:30:34', 'vomissement');

-- --------------------------------------------------------

--
-- Structure de la table `factures`
--

CREATE TABLE `factures` (
  `id` int(11) NOT NULL,
  `medecin_id` int(11) DEFAULT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `consultation_id` int(11) DEFAULT NULL,
  `montant_total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `factures`
--

INSERT INTO `factures` (`id`, `medecin_id`, `patient_id`, `consultation_id`, `montant_total`) VALUES
(12, NULL, NULL, NULL, 21000),
(13, NULL, NULL, NULL, 20000),
(14, NULL, NULL, NULL, 14400),
(15, NULL, NULL, NULL, 10000),
(16, NULL, NULL, NULL, 26000),
(17, NULL, NULL, NULL, 21000),
(18, NULL, NULL, NULL, 32400),
(19, NULL, NULL, NULL, 19000);

-- --------------------------------------------------------

--
-- Structure de la table `facture_contenus`
--

CREATE TABLE `facture_contenus` (
  `id` int(11) NOT NULL,
  `facture_id` int(11) DEFAULT NULL,
  `designation` varchar(255) NOT NULL,
  `medicament_id` int(11) DEFAULT NULL,
  `quantite` varchar(11) NOT NULL,
  `prixUnitaire` double NOT NULL,
  `sousTotal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `facture_contenus`
--

INSERT INTO `facture_contenus` (`id`, `facture_id`, `designation`, `medicament_id`, `quantite`, `prixUnitaire`, `sousTotal`) VALUES
(5, 12, 'Amoxicilline', NULL, '3', 15000, 5000),
(6, 12, 'Comprimé', NULL, '3', 6000, 2000),
(9, 14, 'Comprimé', NULL, '3', 6000, 2000),
(10, 14, 'Comprimé', NULL, '4', 4800, 1200),
(11, 14, 'Comprimé', NULL, '3', 3600, 1200),
(12, 15, 'Amoxicilline', NULL, '2', 10000, 5000),
(13, 17, 'Amoxicilline', NULL, '3', 15000, 5000),
(14, 17, 'Comprimé', NULL, '3', 6000, 2000),
(15, 18, 'Amoxicilline', NULL, '3', 15000, 5000),
(16, 18, 'Comprimé', NULL, '2', 2400, 1200),
(17, 18, 'Metronidazole', NULL, '3', 15000, 5000),
(18, 19, 'Amoxicilline', NULL, '3', 15000, 5000),
(19, 19, 'Comprimé', NULL, '2', 4000, 2000);

-- --------------------------------------------------------

--
-- Structure de la table `medecins`
--

CREATE TABLE `medecins` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `categorie` varchar(11) NOT NULL,
  `etat` varchar(255) NOT NULL COMMENT '1:disponible/2:non disponible'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `medecins`
--

INSERT INTO `medecins` (`id`, `nom`, `telephone`, `categorie`, `etat`) VALUES
(1, 'Rakotoson Vololontsoa Aglae', '324015284', 'Généraliste', 'Disponible'),
(2, 'Rabenjamin Joseph', '332567898', 'Spécialiste', 'Disponible'),
(4, 'Rasoanaivo Jeanne', '0321232145', 'Spécialiste', 'Indisponible');

-- --------------------------------------------------------

--
-- Structure de la table `medicaments`
--

CREATE TABLE `medicaments` (
  `id` int(11) NOT NULL,
  `designation` varchar(255) NOT NULL,
  `prix` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `medicaments`
--

INSERT INTO `medicaments` (`id`, `designation`, `prix`) VALUES
(1, 'Amoxicilline', '5000'),
(3, 'Comprimé', '2000'),
(5, 'Comprimé', '1200'),
(6, 'Metronidazole', '5000');

-- --------------------------------------------------------

--
-- Structure de la table `patients`
--

CREATE TABLE `patients` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `patients`
--

INSERT INTO `patients` (`id`, `nom`, `prenom`, `adresse`, `telephone`) VALUES
(1, 'Chrysostome', 'Priscillia', '1803 67ha Nord-Est', '346362581'),
(2, 'Andriamialy', 'Nisaina Angelot', 'Ankazotokana-Ambanidia', '349265268'),
(4, 'Chrysostome', 'Adams', '67', '0324015284'),
(5, 'Randrianambinina', 'Luisa ', 'Ambohipo', '0345623143');

-- --------------------------------------------------------

--
-- Structure de la table `post_consultation`
--

CREATE TABLE `post_consultation` (
  `id` int(11) NOT NULL,
  `patient` varchar(255) NOT NULL,
  `medecin` varchar(255) NOT NULL,
  `date_consultation` datetime NOT NULL,
  `observations` varchar(255) NOT NULL,
  `facture_id` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `categorie_medecins`
--
ALTER TABLE `categorie_medecins`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `consultations`
--
ALTER TABLE `consultations`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `factures`
--
ALTER TABLE `factures`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `facture_contenus`
--
ALTER TABLE `facture_contenus`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `medecins`
--
ALTER TABLE `medecins`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `medicaments`
--
ALTER TABLE `medicaments`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `post_consultation`
--
ALTER TABLE `post_consultation`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `consultations`
--
ALTER TABLE `consultations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `factures`
--
ALTER TABLE `factures`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `facture_contenus`
--
ALTER TABLE `facture_contenus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `medecins`
--
ALTER TABLE `medecins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `medicaments`
--
ALTER TABLE `medicaments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
