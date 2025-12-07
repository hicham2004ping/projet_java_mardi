-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : dim. 07 déc. 2025 à 13:52
-- Version du serveur : 8.0.43
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `cabinet_medical`
--

-- --------------------------------------------------------

--
-- Structure de la table `acte`
--

CREATE TABLE `acte` (
                        `id` int NOT NULL,
                        `categorie` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `libelle` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `prix_de_base` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `acte`
--

INSERT INTO `acte` (`id`, `categorie`, `libelle`, `prix_de_base`) VALUES
                                                                      (1, 'Consultation', 'Consultation simple', 80),
                                                                      (2, 'Consultation', 'Contrôle de routine', 60),
                                                                      (3, 'Soins', 'Détartrage', 150),
                                                                      (4, 'Soins', 'Polissage dent', 70),
                                                                      (5, 'Soins', 'Traitement carie simple', 200),
                                                                      (6, 'Soins', 'Plombage composite', 250),
                                                                      (7, 'Radiologie', 'Radio panoramique', 180),
                                                                      (8, 'Radiologie', 'Radio dentaire', 90),
                                                                      (9, 'Chirurgie', 'Extraction dent simple', 300),
                                                                      (10, 'Chirurgie', 'Extraction dent de sagesse', 600),
                                                                      (11, 'Prothèse', 'Couronne céramique', 2200),
                                                                      (12, 'Prothèse', 'Bridge 3 éléments', 4200),
                                                                      (13, 'Prothèse', 'Prothèse amovible partielle', 3000),
                                                                      (14, 'Esthétique', 'Blanchiment dentaire', 1800),
                                                                      (15, 'Esthétique', 'Facette dentaire', 2500),
                                                                      (16, 'Orthodontie', 'Pose bagues métalliques', 4500),
                                                                      (17, 'Orthodontie', 'Réglage appareil', 300),
                                                                      (18, 'Hygiène', 'Nettoyage complet', 120),
                                                                      (19, 'Hygiène', 'Application fluor', 90),
                                                                      (20, 'Urgence', 'Traitement douleur dentaire', 150),
                                                                      (21, 'ibrahim', 'akil', 1000),
                                                                      (22, 'ibrahim', 'akil', 1000);

-- --------------------------------------------------------

--
-- Structure de la table `antecedent`
--

CREATE TABLE `antecedent` (
                              `idAntecedent` int NOT NULL,
                              `nom` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `categorie` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `idRisque` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `antecedent`
--

INSERT INTO `antecedent` (`idAntecedent`, `nom`, `categorie`, `idRisque`) VALUES
                                                                              (1, 'Hypertension artérielle', 'Maladie chronique', 4),
                                                                              (2, 'Diabète de type 2', 'Maladie chronique', 3),
                                                                              (3, 'Allergie sévère au pollen', 'Allergie', 1),
                                                                              (4, 'Antécédent d’AVC', 'Neurologique', 2),
                                                                              (5, 'Asthme modéré', 'Respiratoire', 3),
                                                                              (6, 'Crise cardiaque (Infarctus)', 'Cardiaque', 2),
                                                                              (7, 'Anémie', 'Hématologique', 4),
                                                                              (8, 'Intolérance au lactose', 'Allergie / Digestif', 4),
                                                                              (9, 'Insuffisance rénale', 'Rénal', 2),
                                                                              (10, 'Tabagisme chronique', 'Habitude de vie', 1),
                                                                              (11, 'Chirurgie récente', 'Chirurgical', 3),
                                                                              (12, 'Antécédent familial de diabète', 'Familial', 4),
                                                                              (13, 'Obésité sévère', 'Métabolique', 2),
                                                                              (14, 'Epilepsie', 'Neurologique', 2),
                                                                              (15, 'Hypertension légère', 'Cardiaque', 4);

-- --------------------------------------------------------

--
-- Structure de la table `assurance`
--

CREATE TABLE `assurance` (
                             `idAssurance` int NOT NULL,
                             `libelle` enum('cmss','cnops','ramed','privée','aucune') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `assurance`
--

INSERT INTO `assurance` (`idAssurance`, `libelle`) VALUES
                                                       (1, 'cnops'),
                                                       (2, 'cmss'),
                                                       (3, 'ramed'),
                                                       (4, 'aucune');

-- --------------------------------------------------------

--
-- Structure de la table `cabinetmedical`
--

CREATE TABLE `cabinetmedical` (
                                  `idCabinet` int NOT NULL,
                                  `nom` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `email` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `logo` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `adresse` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `tel1` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `tel2` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `siteweb` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `description` text COLLATE utf8mb4_general_ci,
                                  `instagram` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `facebook` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `certificat`
--

CREATE TABLE `certificat` (
                              `idCert` int NOT NULL,
                              `dateDebut` date DEFAULT NULL,
                              `dateFin` date DEFAULT NULL,
                              `nature` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `noteMedecin` text COLLATE utf8mb4_general_ci,
                              `idDossier` int DEFAULT NULL,
                              `idConsult` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `certificat`
--

INSERT INTO `certificat` (`idCert`, `dateDebut`, `dateFin`, `nature`, `noteMedecin`, `idDossier`, `idConsult`) VALUES
                                                                                                                   (1, '2025-01-15', '2025-01-17', 'Arrêt maladie', 'Fièvre 38°C', 1, 1),
                                                                                                                   (2, '2025-01-20', '2025-01-22', 'Arrêt maladie', 'Douleurs dorsales', 1, 2),
                                                                                                                   (3, '2025-01-18', '2025-01-19', 'Allergie saisonnière', 'Antihistaminiques', 2, 3);

-- --------------------------------------------------------

--
-- Structure de la table `charges`
--

CREATE TABLE `charges` (
                           `idCharge` int NOT NULL,
                           `titre` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `description` text COLLATE utf8mb4_general_ci,
                           `montant` double DEFAULT NULL,
                           `dateCharge` datetime DEFAULT NULL,
                           `idCabinet` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `consultation`
--

CREATE TABLE `consultation` (
                                `idConsult` int NOT NULL,
                                `dateConsult` date DEFAULT NULL,
                                `observationMedecin` text COLLATE utf8mb4_general_ci,
                                `idDossier` int DEFAULT NULL,
                                `idStatut` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `consultation`
--

INSERT INTO `consultation` (`idConsult`, `dateConsult`, `observationMedecin`, `idDossier`, `idStatut`) VALUES
                                                                                                           (1, '2025-01-15', 'Grippe légère, fièvre 38°C', 1, 1),
                                                                                                           (2, '2025-01-20', 'Douleurs musculaires au dos', 1, 2),
                                                                                                           (3, '2025-01-18', 'Allergie saisonnière', 2, 1),
                                                                                                           (4, '2025-01-22', 'Contrôle général — RAS', 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `dossiermedical`
--

CREATE TABLE `dossiermedical` (
                                  `idDossier` int NOT NULL,
                                  `dateCreation` date DEFAULT NULL,
                                  `idPatient` int DEFAULT NULL,
                                  `idMedecin` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `dossiermedical`
--

INSERT INTO `dossiermedical` (`idDossier`, `dateCreation`, `idPatient`, `idMedecin`) VALUES
                                                                                         (1, '2025-01-01', 1, 1),
                                                                                         (2, '2025-01-10', 2, 3),
                                                                                         (3, '2025-01-12', 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE `facture` (
                           `idFact` int NOT NULL,
                           `total` double DEFAULT NULL,
                           `totalpaye` double DEFAULT NULL,
                           `reste` double DEFAULT NULL,
                           `statut` enum('payee','non payee','en attente','annulé') COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `dateFact` datetime DEFAULT NULL,
                           `idSF` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `forme`
--

CREATE TABLE `forme` (
                         `idForme` int NOT NULL,
                         `libelle` enum('Comprimé','Gelule','Sirop','Pommade','Injection') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `intervention_medcin`
--

CREATE TABLE `intervention_medcin` (
                                       `id` int NOT NULL,
                                       `numero_dent` int DEFAULT NULL,
                                       `prix_patient` int DEFAULT NULL,
                                       `id_acte` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `medecin`
--

CREATE TABLE `medecin` (
                           `idUser` int NOT NULL,
                           `specialite` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `agendaMensuel` text COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `medecin`
--

INSERT INTO `medecin` (`idUser`, `specialite`, `agendaMensuel`) VALUES
                                                                    (1, 'Cardiologie', 'Lundi 9-12, Mardi 14-17'),
                                                                    (3, 'Dermatologie', 'Mercredi 10-13, Jeudi 15-18');

-- --------------------------------------------------------

--
-- Structure de la table `medicament`
--

CREATE TABLE `medicament` (
                              `idMed` int NOT NULL,
                              `nom` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `laboratoire` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `remboursable` tinyint(1) DEFAULT NULL,
                              `prixUnit` double DEFAULT NULL,
                              `description` text COLLATE utf8mb4_general_ci,
                              `idForme` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `niveaurisque`
--

CREATE TABLE `niveaurisque` (
                                `idRisque` int NOT NULL,
                                `libelle` enum('Faible','Modéré','Dangereux','Très dangereux') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `niveaurisque`
--

INSERT INTO `niveaurisque` (`idRisque`, `libelle`) VALUES
                                                       (1, 'Dangereux'),
                                                       (2, 'Très dangereux'),
                                                       (3, 'Modéré'),
                                                       (4, 'Faible');

-- --------------------------------------------------------

--
-- Structure de la table `ordonnance`
--

CREATE TABLE `ordonnance` (
                              `idOrd` int NOT NULL,
                              `dateOrd` date DEFAULT NULL,
                              `idDossier` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

CREATE TABLE `patient` (
                           `idPatient` int NOT NULL,
                           `nom` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `dateNaissance` date DEFAULT NULL,
                           `adresse` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `telephone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `idSexe` int DEFAULT NULL,
                           `idAssurance` int DEFAULT NULL,
                           `prenom` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `patient`
--

INSERT INTO `patient` (`idPatient`, `nom`, `dateNaissance`, `adresse`, `telephone`, `idSexe`, `idAssurance`, `prenom`, `email`) VALUES
                                                                                                                                    (1, 'Bennani', '1998-06-12', 'Rabat, Agdal', '0611223344', 1, 1, 'Omar', 'omar.bennani@gmail.com'),
                                                                                                                                    (2, 'El Fassi', '2000-09-22', 'Casablanca, Maarif', '0677889900', 2, 2, 'Salma', 'salma.elfassi@gmail.com'),
                                                                                                                                    (3, 'Hassan', '1995-01-05', 'Fès, Centre Ville', '0655667788', 1, 1, 'Youssef', 'youssef.hassan@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `patient_antecedent`
--

CREATE TABLE `patient_antecedent` (
                                      `id` int NOT NULL,
                                      `id_patient` int DEFAULT NULL,
                                      `id_antecedent` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `prescription`
--

CREATE TABLE `prescription` (
                                `idPr` int NOT NULL,
                                `quantite` int DEFAULT NULL,
                                `frequence` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                `dureeEnJours` int DEFAULT NULL,
                                `idOrd` int DEFAULT NULL,
                                `idMed` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `rdv`
--

CREATE TABLE `rdv` (
                       `idRDV` int NOT NULL,
                       `dateRDV` date DEFAULT NULL,
                       `heure` time DEFAULT NULL,
                       `motif` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
                       `noteMedecin` text COLLATE utf8mb4_general_ci,
                       `idPatient` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `revenus`
--

CREATE TABLE `revenus` (
                           `idRev` int NOT NULL,
                           `type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `description` text COLLATE utf8mb4_general_ci,
                           `montant` double DEFAULT NULL,
                           `dateRev` datetime DEFAULT NULL,
                           `idCabinet` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
                        `idRole` int NOT NULL,
                        `libelle` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`idRole`, `libelle`) VALUES
                                             (1, 'Medecin'),
                                             (2, 'Secretaire');

-- --------------------------------------------------------

--
-- Structure de la table `secretaire`
--

CREATE TABLE `secretaire` (
                              `idUser` int NOT NULL,
                              `numCNSS` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `commission` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `sexe`
--

CREATE TABLE `sexe` (
                        `idSexe` int NOT NULL,
                        `libelle` enum('homme','femme') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `sexe`
--

INSERT INTO `sexe` (`idSexe`, `libelle`) VALUES
                                             (1, 'homme'),
                                             (2, 'femme');

-- --------------------------------------------------------

--
-- Structure de la table `situationfinanciere`
--

CREATE TABLE `situationfinanciere` (
                                       `idSF` int NOT NULL,
                                       `totalActes` double DEFAULT NULL,
                                       `totalPaye` double DEFAULT NULL,
                                       `credit` double DEFAULT NULL,
                                       `statut` enum('payee','non payee','en attente','annulé') COLLATE utf8mb4_general_ci DEFAULT NULL,
                                       `enPromo` enum('Oui','Non') COLLATE utf8mb4_general_ci DEFAULT NULL,
                                       `idPatient` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `staff`
--

CREATE TABLE `staff` (
                         `idStaff` int NOT NULL,
                         `salaire` double DEFAULT NULL,
                         `prime` double DEFAULT NULL,
                         `dateRecrutement` date DEFAULT NULL,
                         `soldeConge` int DEFAULT NULL,
                         `idMedecin` int DEFAULT NULL,
                         `idSecretaire` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `statutconsultation`
--

CREATE TABLE `statutconsultation` (
                                      `idStatut` int NOT NULL,
                                      `libelle` enum('En cours','Terminée','Annulé','En attente') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `statutconsultation`
--

INSERT INTO `statutconsultation` (`idStatut`, `libelle`) VALUES
                                                             (1, 'En attente'),
                                                             (2, 'Terminée'),
                                                             (3, 'Annulé'),
                                                             (4, 'En cours');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
                               `idUser` int NOT NULL,
                               `nom` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               `email` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               `adresse` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               `cin` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               `tel` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               `idSexe` int DEFAULT NULL,
                               `login` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               `motdepasse` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               `dateNaissance` date DEFAULT NULL,
                               `lastLoginDate` datetime DEFAULT NULL,
                               `idRole` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`idUser`, `nom`, `email`, `adresse`, `cin`, `tel`, `idSexe`, `login`, `motdepasse`, `dateNaissance`, `lastLoginDate`, `idRole`) VALUES
                                                                                                                                                               (1, 'Karim El Mansouri', 'karim.mansouri@example.com', '12 Rue Al Qods, Casablanca', 'J123456', '0612345678', 1, 'karim_m', '1234@Pass', '1998-05-12', '2025-11-15 14:32:10', 1),
                                                                                                                                                               (2, 'Sara Ilyas', 'ilyasmoulragouba@gmail.com', 'Rabat', 'Fjfajf', '0281938192', 2, 'salut', 'Pass@1234', '1995-09-20', NULL, 2),
                                                                                                                                                               (3, 'Nabil Zaki', 'nabil.zaki@example.com', 'Casablanca', 'K987654', '0655123456', 1, 'nabil_z', 'Pass@2025', '1990-02-10', NULL, 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `acte`
--
ALTER TABLE `acte`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `antecedent`
--
ALTER TABLE `antecedent`
    ADD PRIMARY KEY (`idAntecedent`),
  ADD KEY `idRisque` (`idRisque`);

--
-- Index pour la table `assurance`
--
ALTER TABLE `assurance`
    ADD PRIMARY KEY (`idAssurance`);

--
-- Index pour la table `cabinetmedical`
--
ALTER TABLE `cabinetmedical`
    ADD PRIMARY KEY (`idCabinet`);

--
-- Index pour la table `certificat`
--
ALTER TABLE `certificat`
    ADD PRIMARY KEY (`idCert`),
  ADD KEY `idDossier` (`idDossier`),
  ADD KEY `idConsult` (`idConsult`);

--
-- Index pour la table `charges`
--
ALTER TABLE `charges`
    ADD PRIMARY KEY (`idCharge`),
  ADD KEY `idCabinet` (`idCabinet`);

--
-- Index pour la table `consultation`
--
ALTER TABLE `consultation`
    ADD PRIMARY KEY (`idConsult`),
  ADD KEY `idDossier` (`idDossier`),
  ADD KEY `idStatut` (`idStatut`);

--
-- Index pour la table `dossiermedical`
--
ALTER TABLE `dossiermedical`
    ADD PRIMARY KEY (`idDossier`),
  ADD KEY `idPatient` (`idPatient`),
  ADD KEY `idMedecin` (`idMedecin`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
    ADD PRIMARY KEY (`idFact`),
  ADD KEY `idSF` (`idSF`);

--
-- Index pour la table `forme`
--
ALTER TABLE `forme`
    ADD PRIMARY KEY (`idForme`);

--
-- Index pour la table `intervention_medcin`
--
ALTER TABLE `intervention_medcin`
    ADD PRIMARY KEY (`id`),
  ADD KEY `intervention_medcin_ibfk_1` (`id_acte`);

--
-- Index pour la table `medecin`
--
ALTER TABLE `medecin`
    ADD PRIMARY KEY (`idUser`);

--
-- Index pour la table `medicament`
--
ALTER TABLE `medicament`
    ADD PRIMARY KEY (`idMed`),
  ADD KEY `idForme` (`idForme`);

--
-- Index pour la table `niveaurisque`
--
ALTER TABLE `niveaurisque`
    ADD PRIMARY KEY (`idRisque`);

--
-- Index pour la table `ordonnance`
--
ALTER TABLE `ordonnance`
    ADD PRIMARY KEY (`idOrd`),
  ADD KEY `idDossier` (`idDossier`);

--
-- Index pour la table `patient`
--
ALTER TABLE `patient`
    ADD PRIMARY KEY (`idPatient`),
  ADD KEY `idSexe` (`idSexe`),
  ADD KEY `idAssurance` (`idAssurance`);

--
-- Index pour la table `patient_antecedent`
--
ALTER TABLE `patient_antecedent`
    ADD PRIMARY KEY (`id`),
  ADD KEY `patient_antecedent_ibfk_1` (`id_patient`),
  ADD KEY `patient_antecedent_ibfk_2` (`id_antecedent`);

--
-- Index pour la table `prescription`
--
ALTER TABLE `prescription`
    ADD PRIMARY KEY (`idPr`),
  ADD KEY `idOrd` (`idOrd`),
  ADD KEY `idMed` (`idMed`);

--
-- Index pour la table `rdv`
--
ALTER TABLE `rdv`
    ADD PRIMARY KEY (`idRDV`),
  ADD KEY `idPatient` (`idPatient`);

--
-- Index pour la table `revenus`
--
ALTER TABLE `revenus`
    ADD PRIMARY KEY (`idRev`),
  ADD KEY `idCabinet` (`idCabinet`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
    ADD PRIMARY KEY (`idRole`);

--
-- Index pour la table `secretaire`
--
ALTER TABLE `secretaire`
    ADD PRIMARY KEY (`idUser`);

--
-- Index pour la table `sexe`
--
ALTER TABLE `sexe`
    ADD PRIMARY KEY (`idSexe`);

--
-- Index pour la table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
    ADD PRIMARY KEY (`idSF`),
  ADD KEY `idPatient` (`idPatient`);

--
-- Index pour la table `staff`
--
ALTER TABLE `staff`
    ADD PRIMARY KEY (`idStaff`),
  ADD KEY `idMedecin` (`idMedecin`),
  ADD KEY `idSecretaire` (`idSecretaire`);

--
-- Index pour la table `statutconsultation`
--
ALTER TABLE `statutconsultation`
    ADD PRIMARY KEY (`idStatut`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
    ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `login` (`login`),
  ADD KEY `idRole` (`idRole`),
  ADD KEY `idSexe` (`idSexe`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `antecedent`
--
ALTER TABLE `antecedent`
    MODIFY `idAntecedent` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `assurance`
--
ALTER TABLE `assurance`
    MODIFY `idAssurance` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `cabinetmedical`
--
ALTER TABLE `cabinetmedical`
    MODIFY `idCabinet` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `certificat`
--
ALTER TABLE `certificat`
    MODIFY `idCert` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `charges`
--
ALTER TABLE `charges`
    MODIFY `idCharge` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `consultation`
--
ALTER TABLE `consultation`
    MODIFY `idConsult` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `dossiermedical`
--
ALTER TABLE `dossiermedical`
    MODIFY `idDossier` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
    MODIFY `idFact` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `forme`
--
ALTER TABLE `forme`
    MODIFY `idForme` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `medicament`
--
ALTER TABLE `medicament`
    MODIFY `idMed` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `niveaurisque`
--
ALTER TABLE `niveaurisque`
    MODIFY `idRisque` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `ordonnance`
--
ALTER TABLE `ordonnance`
    MODIFY `idOrd` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `patient`
--
ALTER TABLE `patient`
    MODIFY `idPatient` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `prescription`
--
ALTER TABLE `prescription`
    MODIFY `idPr` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `rdv`
--
ALTER TABLE `rdv`
    MODIFY `idRDV` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `revenus`
--
ALTER TABLE `revenus`
    MODIFY `idRev` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
    MODIFY `idRole` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `sexe`
--
ALTER TABLE `sexe`
    MODIFY `idSexe` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
    MODIFY `idSF` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `staff`
--
ALTER TABLE `staff`
    MODIFY `idStaff` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `statutconsultation`
--
ALTER TABLE `statutconsultation`
    MODIFY `idStatut` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
    MODIFY `idUser` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `antecedent`
--
ALTER TABLE `antecedent`
    ADD CONSTRAINT `antecedent_ibfk_1` FOREIGN KEY (`idRisque`) REFERENCES `niveaurisque` (`idRisque`) ON DELETE CASCADE;

--
-- Contraintes pour la table `certificat`
--
ALTER TABLE `certificat`
    ADD CONSTRAINT `certificat_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`) ON DELETE CASCADE,
  ADD CONSTRAINT `certificat_ibfk_2` FOREIGN KEY (`idConsult`) REFERENCES `consultation` (`idConsult`) ON DELETE CASCADE;

--
-- Contraintes pour la table `charges`
--
ALTER TABLE `charges`
    ADD CONSTRAINT `charges_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`) ON DELETE CASCADE;

--
-- Contraintes pour la table `consultation`
--
ALTER TABLE `consultation`
    ADD CONSTRAINT `consultation_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`) ON DELETE CASCADE,
  ADD CONSTRAINT `consultation_ibfk_2` FOREIGN KEY (`idStatut`) REFERENCES `statutconsultation` (`idStatut`) ON DELETE CASCADE;

--
-- Contraintes pour la table `dossiermedical`
--
ALTER TABLE `dossiermedical`
    ADD CONSTRAINT `dossiermedical_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE,
  ADD CONSTRAINT `dossiermedical_ibfk_2` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`) ON DELETE CASCADE;

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
    ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`idSF`) REFERENCES `situationfinanciere` (`idSF`) ON DELETE CASCADE;

--
-- Contraintes pour la table `intervention_medcin`
--
ALTER TABLE `intervention_medcin`
    ADD CONSTRAINT `intervention_medcin_ibfk_1` FOREIGN KEY (`id_acte`) REFERENCES `acte` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `medecin`
--
ALTER TABLE `medecin`
    ADD CONSTRAINT `medecin_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`) ON DELETE CASCADE;

--
-- Contraintes pour la table `medicament`
--
ALTER TABLE `medicament`
    ADD CONSTRAINT `medicament_ibfk_1` FOREIGN KEY (`idForme`) REFERENCES `forme` (`idForme`) ON DELETE CASCADE;

--
-- Contraintes pour la table `ordonnance`
--
ALTER TABLE `ordonnance`
    ADD CONSTRAINT `ordonnance_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`) ON DELETE CASCADE;

--
-- Contraintes pour la table `patient`
--
ALTER TABLE `patient`
    ADD CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`) ON DELETE CASCADE,
  ADD CONSTRAINT `patient_ibfk_2` FOREIGN KEY (`idAssurance`) REFERENCES `assurance` (`idAssurance`) ON DELETE CASCADE;

--
-- Contraintes pour la table `patient_antecedent`
--
ALTER TABLE `patient_antecedent`
    ADD CONSTRAINT `patient_antecedent_ibfk_1` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE,
  ADD CONSTRAINT `patient_antecedent_ibfk_2` FOREIGN KEY (`id_antecedent`) REFERENCES `antecedent` (`idAntecedent`) ON DELETE CASCADE;

--
-- Contraintes pour la table `prescription`
--
ALTER TABLE `prescription`
    ADD CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`idOrd`) REFERENCES `ordonnance` (`idOrd`) ON DELETE CASCADE,
  ADD CONSTRAINT `prescription_ibfk_2` FOREIGN KEY (`idMed`) REFERENCES `medicament` (`idMed`) ON DELETE CASCADE;

--
-- Contraintes pour la table `rdv`
--
ALTER TABLE `rdv`
    ADD CONSTRAINT `rdv_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE;

--
-- Contraintes pour la table `revenus`
--
ALTER TABLE `revenus`
    ADD CONSTRAINT `revenus_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`) ON DELETE CASCADE;

--
-- Contraintes pour la table `secretaire`
--
ALTER TABLE `secretaire`
    ADD CONSTRAINT `secretaire_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`) ON DELETE CASCADE;

--
-- Contraintes pour la table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
    ADD CONSTRAINT `situationfinanciere_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE;

--
-- Contraintes pour la table `staff`
--
ALTER TABLE `staff`
    ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`) ON DELETE CASCADE,
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`idSecretaire`) REFERENCES `secretaire` (`idUser`) ON DELETE CASCADE;

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
    ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE CASCADE,
  ADD CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
