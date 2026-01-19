-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 08, 2025 at 09:45 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cabinet_medical`
--


-- --------------------------------------------------------------------------------
-- Table structure for table `acte`
--

CREATE TABLE `acte` (
  `id` int(11) NOT NULL,
  `categorie` varchar(30) DEFAULT NULL,
  `libelle` varchar(30) DEFAULT NULL,
  `prix_de_base` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `acte`
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
(20, 'Urgence', 'Traitement douleur dentaire', 10000);

-- --------------------------------------------------------
    -- admin
CREATE TABLE admin (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL,
                       nom VARCHAR(100),
                       password_hash VARCHAR(255) NOT NULL,
                       email VARCHAR(150) UNIQUE,
                       role VARCHAR(50),
                       lastLoginDate DATETIME,
                       idRole INT
);

-- --------------------------------------------------------
--
-- Table structure for table `antecedent`
--

CREATE TABLE `antecedent` (
  `idAntecedent` int(11) NOT NULL,
  `nom` varchar(150) DEFAULT NULL,
  `categorie` varchar(100) DEFAULT NULL,
  `idRisque` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `antecedent`
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
(14, 'blessure_au_jambe', 'allergie', 1);

-- --------------------------------------------------------

--
-- Table structure for table `assurance`
--

CREATE TABLE `assurance` (
  `idAssurance` int(11) NOT NULL,
  `libelle` enum('cmss','cnops','ramed','privée','aucune') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `assurance`
--

INSERT INTO `assurance` (`idAssurance`, `libelle`) VALUES
(1, 'cnops'),
(2, 'cmss'),
(3, 'ramed'),
(4, 'aucune');

-- --------------------------------------------------------

--
-- Table structure for table `cabinetmedical`
--

CREATE TABLE `cabinetmedical` (
  `idCabinet` int(11) NOT NULL,
  `nom` varchar(150) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `logo` varchar(150) DEFAULT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  `tel1` varchar(20) DEFAULT NULL,
  `tel2` varchar(20) DEFAULT NULL,
  `siteweb` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `instagram` varchar(100) DEFAULT NULL,
  `facebook` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `certificat`
--

CREATE TABLE `certificat` (
  `idCert` int(11) NOT NULL,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `nature` varchar(200) DEFAULT NULL,
  `noteMedecin` text DEFAULT NULL,
  `idDossier` int(11) DEFAULT NULL,
  `id_consultation` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `charges`
--

CREATE TABLE `charges` (
  `idCharge` int(11) NOT NULL,
  `titre` varchar(150) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `dateCharge` datetime DEFAULT NULL,
  `idCabinet` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `consultation`
--

CREATE TABLE `consultation` (
  `idConsult` int(11) NOT NULL,
  `dateConsult` date DEFAULT NULL,
  `observationMedecin` text DEFAULT NULL,
  `idDossier` int(11) DEFAULT NULL,
  `idStatut` int(11) DEFAULT NULL,
  `id_rdv` int(11) DEFAULT NULL,
  `id_medecin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dossiermedical`
--

CREATE TABLE `dossiermedical` (
  `idDossier` int(11) NOT NULL,
  `dateCreation` date DEFAULT NULL,
  `idPatient` int(11) DEFAULT NULL,
  `idMedecin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dossiermedical`
--

INSERT INTO `dossiermedical` (`idDossier`, `dateCreation`, `idPatient`, `idMedecin`) VALUES
(6, '2025-12-07', 16, 1);

-- --------------------------------------------------------
--
-- Table structure for table `file_attente`
--
CREATE TABLE `file_attente` (
  `idFileAttente` int(11) NOT NULL AUTO_INCREMENT,
  `idDossier` int(11) NOT NULL,
  `dateFile` date NOT NULL,
  `statut` enum('En attente','En consultation','Terminé') DEFAULT 'En attente',
  `position` int(11) DEFAULT 1,
  `dateArrivee` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idFileAttente`),
  KEY `idDossier` (`idDossier`),
  KEY `dateFile` (`dateFile`),
  UNIQUE KEY `unique_daily_queue` (`idDossier`, `dateFile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

CREATE TABLE `facture` (
  `idFact` int(11) NOT NULL,
  `total` double DEFAULT NULL,
  `totalpaye` double DEFAULT NULL,
  `reste` double DEFAULT NULL,
  `statut` enum('payee','non payee','en attente','annulé') DEFAULT NULL,
  `dateFact` datetime DEFAULT NULL,
  `idSF` int(11) DEFAULT NULL,
  `id_consultation` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `forme`
--

CREATE TABLE `forme` (
  `idForme` int(11) NOT NULL,
  `libelle` enum('Comprimé','Gelule','Sirop','Pommade','Injection') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `forme`
--

INSERT INTO `forme` (`idForme`, `libelle`) VALUES
(1, 'Gelule'),
(2, 'Sirop'),
(3, 'Pommade'),
(4, 'Injection'),
(5, 'Comprimé');

-- --------------------------------------------------------

--
-- Table structure for table `intervention_medcin`
--

CREATE TABLE `intervention_medcin` (
  `id` int(11) NOT NULL,
  `numero_dent` int(11) DEFAULT NULL,
  `prix_patient` int(11) DEFAULT NULL,
  `id_acte` int(11) DEFAULT NULL,
  `id_consultation` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `medecin`
--

CREATE TABLE `medecin` (
  `idUser` int(11) NOT NULL,
  `specialite` varchar(100) DEFAULT NULL,
  `agendaMensuel` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medecin`
--

INSERT INTO `medecin` (`idUser`, `specialite`, `agendaMensuel`) VALUES
(1, 'Dentiste', 'c\'est juste un petit test pour le mardi matin ');

-- --------------------------------------------------------

--
-- Table structure for table `medicament`
--

CREATE TABLE `medicament` (
  `idMed` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `laboratoire` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `remboursable` tinyint(1) DEFAULT NULL,
  `prixUnit` double DEFAULT NULL,
  `description` text DEFAULT NULL,
  `idForme` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicament`
--

INSERT INTO `medicament` (`idMed`, `nom`, `laboratoire`, `type`, `remboursable`, `prixUnit`, `description`, `idForme`) VALUES
(1, 'doliprane', 'emsi_lab', 'simple', 0, 17.5, 'pour le mal de tete ', 5),
(2, 'Ibuprofène', 'pharma_plus', 'anti-inflammatoire', 1, 12.5, 'Traitement des douleurs et inflammations', 5),
(3, 'Amoxicilline', 'medilab', 'antibiotique', 1, 19.9, 'Antibiotique contre infections buccales', 1),
(4, 'Nurofen', 'healprov', 'antalgique', 0, 15, 'Soulagement temporaire de la douleur', 5),
(5, 'Biseptine', 'antisept_lab', 'antiseptique', 1, 10.8, 'Désinfection des plaies légères', 2),
(6, 'Fungiderm', 'dermcare', 'antifongique', 1, 14.7, 'Traitement local contre infections fongiques', 3),
(7, 'Lidocaïne', 'oraltech', 'anesthésique', 0, 25.5, 'Anesthésie locale avant soin dentaire', 4),
(8, 'Spasfon', 'biohealth', 'antispasmodique', 0, 11.3, 'Traitement contre les spasmes légers', 5),
(9, 'Vitamine C', 'nutripharma', 'complément', 0, 8.9, 'Renforcement du système immunitaire', 1),
(10, 'Doloflam', 'dentimed', 'anti-inflammatoire', 1, 16.4, 'Douleurs dentaires et gingivales', 5),
(11, 'Azithromycine', 'labomed', 'antibiotique', 1, 22.7, 'Infections sévères ORL et buccales', 1),
(12, 'Calmopsy', 'medlight', 'sédatif léger', 0, 13.2, 'Aide temporaire à la relaxation', 2);

-- --------------------------------------------------------

--
-- Table structure for table `niveaurisque`
--

CREATE TABLE `niveaurisque` (
  `idRisque` int(11) NOT NULL,
  `libelle` enum('Faible','Modéré','Dangereux','Très dangereux') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `niveaurisque`
--

INSERT INTO `niveaurisque` (`idRisque`, `libelle`) VALUES
(1, 'Dangereux'),
(2, 'Très dangereux'),
(3, 'Modéré'),
(4, 'Faible');

-- --------------------------------------------------------

--
-- Table structure for table `ordonnance`
--

CREATE TABLE `ordonnance` (
  `idOrd` int(11) NOT NULL,
  `dateOrd` date DEFAULT NULL,
  `idDossier` int(11) DEFAULT NULL,
  `id_consultation` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `idPatient` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `idSexe` int(11) DEFAULT NULL,
  `idAssurance` int(11) DEFAULT NULL,
  `prenom` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`idPatient`, `nom`, `dateNaissance`, `adresse`, `telephone`, `idSexe`, `idAssurance`, `prenom`, `email`) VALUES
(1, 'ibrahim', '2024-09-20', 'beirut', '021831241', 1, 2, NULL, NULL),
(2, 'nascerallah', '2024-09-20', 'beirut', '021831241', 1, 1, NULL, NULL),
(3, 'hassan', '2024-09-27', 'Beirut', '0777181657', 2, 1, 'nascerallah', 'hezbollah@gmail.com'),
(14, 'safiyeddine', '2024-10-03', 'Dahia', '0777181657', 1, 2, 'hachem', 'hezbollah'),
(15, 'safiyeddine', '2024-10-03', 'Dahia', '0777181657', 1, 2, 'hachem', 'hezbollah'),
(16, 'safiyeddine', '2024-10-03', 'Dahia', '0777181657', 1, 2, 'hachem', 'hezbollah'),
(34, 'akil', '2025-12-06', 'alkhiyam', '06xxxxxx', 1, 1, 'ibrahim', 'email@domaine');

-- --------------------------------------------------------

--
-- Table structure for table `patient_antecedent`
--

CREATE TABLE `patient_antecedent` (
  `id` int(11) NOT NULL,
  `id_patient` int(11) DEFAULT NULL,
  `id_antecedent` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_antecedent`
--

INSERT INTO `patient_antecedent` (`id`, `id_patient`, `id_antecedent`) VALUES
(1, 15, 1),
(2, 15, 2),
(3, 15, 3),
(4, 15, 4),
(5, 15, 5);

-- --------------------------------------------------------

--
-- Table structure for table `prescription`
--

CREATE TABLE `prescription` (
  `idPr` int(11) NOT NULL,
  `quantite` int(11) DEFAULT NULL,
  `frequence` varchar(50) DEFAULT NULL,
  `dureeEnJours` int(11) DEFAULT NULL,
  `idOrd` int(11) DEFAULT NULL,
  `idMed` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rdv`
--

CREATE TABLE `rdv` (
  `idRDV` int(11) NOT NULL,
  `dateRDV` date DEFAULT NULL,
  `heure` time DEFAULT NULL,
  `motif` varchar(150) DEFAULT NULL,
  `noteMedecin` text DEFAULT NULL,
  `id_dossier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rdv`
--

INSERT INTO `rdv` (`idRDV`, `dateRDV`, `heure`, `motif`, `noteMedecin`, `id_dossier`) VALUES
(1, '2025-12-08', '02:09:00', 'consultation', 'rien', 2);

-- --------------------------------------------------------

--
-- Table structure for table `revenus`
--

CREATE TABLE `revenus` (
  `idRev` int(11) NOT NULL,
  `type` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `dateRev` datetime DEFAULT NULL,
  `idCabinet` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `idRole` int(11) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`idRole`, `libelle`) VALUES
(1, 'Medecin'),
(2, 'Secretaire'),
(3, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `secretaire`
--

CREATE TABLE `secretaire` (
  `idUser` int(11) NOT NULL,
  `numCNSS` varchar(30) DEFAULT NULL,
  `commission` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sexe`
--

CREATE TABLE `sexe` (
  `idSexe` int(11) NOT NULL,
  `libelle` enum('homme','femme') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sexe`
--

INSERT INTO `sexe` (`idSexe`, `libelle`) VALUES
(1, 'homme'),
(2, 'femme');

-- --------------------------------------------------------

--
-- Table structure for table `situationfinanciere`
--

CREATE TABLE `situationfinanciere` (
  `idSF` int(11) NOT NULL,
  `totalActes` double DEFAULT NULL,
  `totalPaye` double DEFAULT NULL,
  `credit` double DEFAULT NULL,
  `statut` enum('payee','non payee','en attente','annulé') DEFAULT NULL,
  `enPromo` enum('Oui','Non') DEFAULT NULL,
  `idPatient` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `idStaff` int(11) NOT NULL,
  `salaire` double DEFAULT NULL,
  `prime` double DEFAULT NULL,
  `dateRecrutement` date DEFAULT NULL,
  `soldeConge` int(11) DEFAULT NULL,
  `idMedecin` int(11) DEFAULT NULL,
  `idSecretaire` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `statutconsultation`
--

CREATE TABLE `statutconsultation` (
  `idStatut` int(11) NOT NULL,
  `libelle` enum('En cours','Terminé','Annulé') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `statutconsultation`
--

INSERT INTO `statutconsultation` (`idStatut`, `libelle`) VALUES
(1, 'En cours'),
(2, 'Terminé'),
(3, 'Annulé');

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `idUser` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  `cin` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `idSexe` int(11) DEFAULT NULL,
  `login` varchar(50) DEFAULT NULL,
  `motdepasse` varchar(255) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL,
  `idRole` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`idUser`, `nom`, `email`, `adresse`, `cin`, `tel`, `idSexe`, `login`, `motdepasse`, `dateNaissance`, `lastLoginDate`, `idRole`) VALUES
(1, 'Karim El Mansouri', 'karim.mansouri@example.com', '12 Rue Al Qods, Casablanca', 'J123456', '0612345678', 1, 'karim_m', '1234@Pass', '1998-05-12', '2025-11-15 14:32:10', 1),
(2, 'sara', 'ilyasmoulragouba@gmail.com', 'rabat', 'Fjfajf', '0281938192', 2, 'salut', 'Pass@1234', '1995-09-20', NULL, 2),
(3,'Othmane CH','othmane@admin.com','rabat','blablabla','0657193175',1,'admin','admin','2004-10-13',NULL,3);
--
-- Indexes for dumped tables
--

--
-- Indexes for table `acte`
--
ALTER TABLE `acte`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `antecedent`
--
ALTER TABLE `antecedent`
  ADD PRIMARY KEY (`idAntecedent`),
  ADD KEY `idRisque` (`idRisque`);

--
-- Indexes for table `assurance`
--
ALTER TABLE `assurance`
  ADD PRIMARY KEY (`idAssurance`);

--
-- Indexes for table `cabinetmedical`
--
ALTER TABLE `cabinetmedical`
  ADD PRIMARY KEY (`idCabinet`);

--
-- Indexes for table `certificat`
--
ALTER TABLE `certificat`
  ADD PRIMARY KEY (`idCert`),
  ADD KEY `idDossier` (`idDossier`),
  ADD KEY `fk_consultation1` (`id_consultation`);

--
-- Indexes for table `charges`
--
ALTER TABLE `charges`
  ADD PRIMARY KEY (`idCharge`),
  ADD KEY `idCabinet` (`idCabinet`);

--
-- Indexes for table `consultation`
--
ALTER TABLE `consultation`
  ADD PRIMARY KEY (`idConsult`),
  ADD KEY `idDossier` (`idDossier`),
  ADD KEY `idStatut` (`idStatut`),
  ADD KEY `fk_rdv` (`id_rdv`);

--
-- Indexes for table `dossiermedical`
--
ALTER TABLE `dossiermedical`
  ADD PRIMARY KEY (`idDossier`),
  ADD KEY `idPatient` (`idPatient`),
  ADD KEY `idMedecin` (`idMedecin`);

--
-- Indexes for table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`idFact`),
  ADD KEY `idSF` (`idSF`),
  ADD KEY `id_consultation` (`id_consultation`);

--
-- Indexes for table `forme`
--
ALTER TABLE `forme`
  ADD PRIMARY KEY (`idForme`);

--
-- Indexes for table `intervention_medcin`
--
ALTER TABLE `intervention_medcin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `intervention_medcin_ibfk_1` (`id_acte`),
  ADD KEY `fk_consultation` (`id_consultation`);

--
-- Indexes for table `medecin`
--
ALTER TABLE `medecin`
  ADD PRIMARY KEY (`idUser`);

--
-- Indexes for table `medicament`
--
ALTER TABLE `medicament`
  ADD PRIMARY KEY (`idMed`),
  ADD KEY `idForme` (`idForme`);

--
-- Indexes for table `niveaurisque`
--
ALTER TABLE `niveaurisque`
  ADD PRIMARY KEY (`idRisque`);

--
-- Indexes for table `ordonnance`
--
ALTER TABLE `ordonnance`
  ADD PRIMARY KEY (`idOrd`),
  ADD KEY `idDossier` (`idDossier`),
  ADD KEY `fk_consultation2` (`id_consultation`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`idPatient`),
  ADD KEY `idSexe` (`idSexe`),
  ADD KEY `idAssurance` (`idAssurance`);

--
-- Indexes for table `patient_antecedent`
--
ALTER TABLE `patient_antecedent`
  ADD PRIMARY KEY (`id`),
  ADD KEY `patient_antecedent_ibfk_1` (`id_patient`),
  ADD KEY `patient_antecedent_ibfk_2` (`id_antecedent`);

--
-- Indexes for table `prescription`
--
ALTER TABLE `prescription`
  ADD PRIMARY KEY (`idPr`),
  ADD KEY `idOrd` (`idOrd`),
  ADD KEY `idMed` (`idMed`);

--
-- Indexes for table `rdv`
--
ALTER TABLE `rdv`
  ADD PRIMARY KEY (`idRDV`),
  ADD KEY `fk_dossier_medical` (`id_dossier`);

--
-- Indexes for table `revenus`
--
ALTER TABLE `revenus`
  ADD PRIMARY KEY (`idRev`),
  ADD KEY `idCabinet` (`idCabinet`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`idRole`);

--
-- Indexes for table `secretaire`
--
ALTER TABLE `secretaire`
  ADD PRIMARY KEY (`idUser`);

--
-- Indexes for table `sexe`
--
ALTER TABLE `sexe`
  ADD PRIMARY KEY (`idSexe`);

--
-- Indexes for table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
  ADD PRIMARY KEY (`idSF`),
  ADD KEY `idPatient` (`idPatient`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`idStaff`),
  ADD KEY `idMedecin` (`idMedecin`),
  ADD KEY `idSecretaire` (`idSecretaire`);

--
-- Indexes for table `statutconsultation`
--
ALTER TABLE `statutconsultation`
  ADD PRIMARY KEY (`idStatut`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `login` (`login`),
  ADD KEY `idRole` (`idRole`),
  ADD KEY `idSexe` (`idSexe`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `antecedent`
--
ALTER TABLE `antecedent`
  MODIFY `idAntecedent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `assurance`
--
ALTER TABLE `assurance`
  MODIFY `idAssurance` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `cabinetmedical`
--
ALTER TABLE `cabinetmedical`
  MODIFY `idCabinet` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `certificat`
--
ALTER TABLE `certificat`
  MODIFY `idCert` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `charges`
--
ALTER TABLE `charges`
  MODIFY `idCharge` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consultation`
--
ALTER TABLE `consultation`
  MODIFY `idConsult` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `dossiermedical`
--
ALTER TABLE `dossiermedical`
  MODIFY `idDossier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `file_attente`
--
ALTER TABLE `file_attente`
  MODIFY `idFileAttente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `facture`
--
ALTER TABLE `facture`
  MODIFY `idFact` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `forme`
--
ALTER TABLE `forme`
  MODIFY `idForme` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `intervention_medcin`
--
ALTER TABLE `intervention_medcin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `medicament`
--
ALTER TABLE `medicament`
  MODIFY `idMed` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `niveaurisque`
--
ALTER TABLE `niveaurisque`
  MODIFY `idRisque` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `ordonnance`
--
ALTER TABLE `ordonnance`
  MODIFY `idOrd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `idPatient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `prescription`
--
ALTER TABLE `prescription`
  MODIFY `idPr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `rdv`
--
ALTER TABLE `rdv`
  MODIFY `idRDV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `revenus`
--
ALTER TABLE `revenus`
  MODIFY `idRev` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `idRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sexe`
--
ALTER TABLE `sexe`
  MODIFY `idSexe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
  MODIFY `idSF` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `idStaff` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `statutconsultation`
--
ALTER TABLE `statutconsultation`
  MODIFY `idStatut` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `antecedent`
--
ALTER TABLE `antecedent`
  ADD CONSTRAINT `antecedent_ibfk_1` FOREIGN KEY (`idRisque`) REFERENCES `niveaurisque` (`idRisque`) ON DELETE CASCADE;

--
-- Constraints for table `certificat`
--
ALTER TABLE `certificat`
  ADD CONSTRAINT `certificat_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_consultation1` FOREIGN KEY (`id_consultation`) REFERENCES `consultation` (`idConsult`);

--
-- Constraints for table `charges`
--
ALTER TABLE `charges`
  ADD CONSTRAINT `charges_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`) ON DELETE CASCADE;

--
-- Constraints for table `consultation`
--
ALTER TABLE `consultation`
  ADD CONSTRAINT `consultation_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`) ON DELETE CASCADE,
  ADD CONSTRAINT `consultation_ibfk_2` FOREIGN KEY (`idStatut`) REFERENCES `statutconsultation` (`idStatut`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_rdv` FOREIGN KEY (`id_rdv`) REFERENCES `rdv` (`idRDV`);

--
-- Constraints for table `dossiermedical`
--
ALTER TABLE `dossiermedical`
  ADD CONSTRAINT `dossiermedical_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE,
  ADD CONSTRAINT `dossiermedical_ibfk_2` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`) ON DELETE CASCADE;

--
-- Constraints for table `file_attente`
--
ALTER TABLE `file_attente`
  ADD CONSTRAINT `file_attente_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`) ON DELETE CASCADE;

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`idSF`) REFERENCES `situationfinanciere` (`idSF`) ON DELETE CASCADE,
  ADD CONSTRAINT `facture_ibfk_2` FOREIGN KEY (`id_consultation`) REFERENCES `consultation` (`idConsult`);

--
-- Constraints for table `intervention_medcin`
--
ALTER TABLE `intervention_medcin`
  ADD CONSTRAINT `fk_consultation` FOREIGN KEY (`id_consultation`) REFERENCES `consultation` (`idConsult`) ON DELETE CASCADE,
  ADD CONSTRAINT `intervention_medcin_ibfk_1` FOREIGN KEY (`id_acte`) REFERENCES `acte` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `medecin`
--
ALTER TABLE `medecin`
  ADD CONSTRAINT `medecin_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`) ON DELETE CASCADE;

--
-- Constraints for table `medicament`
--
ALTER TABLE `medicament`
  ADD CONSTRAINT `medicament_ibfk_1` FOREIGN KEY (`idForme`) REFERENCES `forme` (`idForme`) ON DELETE CASCADE;

--
-- Constraints for table `ordonnance`
--
ALTER TABLE `ordonnance`
  ADD CONSTRAINT `fk_consultation2` FOREIGN KEY (`id_consultation`) REFERENCES `consultation` (`idConsult`) ON DELETE CASCADE,
  ADD CONSTRAINT `ordonnance_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`) ON DELETE CASCADE;

--
-- Constraints for table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`) ON DELETE CASCADE,
  ADD CONSTRAINT `patient_ibfk_2` FOREIGN KEY (`idAssurance`) REFERENCES `assurance` (`idAssurance`) ON DELETE CASCADE;

--
-- Constraints for table `patient_antecedent`
--
ALTER TABLE `patient_antecedent`
  ADD CONSTRAINT `patient_antecedent_ibfk_1` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE,
  ADD CONSTRAINT `patient_antecedent_ibfk_2` FOREIGN KEY (`id_antecedent`) REFERENCES `antecedent` (`idAntecedent`) ON DELETE CASCADE;

--
-- Constraints for table `prescription`
--
ALTER TABLE `prescription`
  ADD CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`idOrd`) REFERENCES `ordonnance` (`idOrd`) ON DELETE CASCADE,
  ADD CONSTRAINT `prescription_ibfk_2` FOREIGN KEY (`idMed`) REFERENCES `medicament` (`idMed`) ON DELETE CASCADE;

--
-- Constraints for table `revenus`
--
ALTER TABLE `revenus`
  ADD CONSTRAINT `revenus_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`) ON DELETE CASCADE;

--
-- Constraints for table `secretaire`
--
ALTER TABLE `secretaire`
  ADD CONSTRAINT `secretaire_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`) ON DELETE CASCADE;

--
-- Constraints for table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
  ADD CONSTRAINT `situationfinanciere_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE;

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`) ON DELETE CASCADE,
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`idSecretaire`) REFERENCES `secretaire` (`idUser`) ON DELETE CASCADE;

--
-- Constraints for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE CASCADE,
  ADD CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`) ON DELETE CASCADE;
--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;
-- --------------------------------------------------------
-- table à ajouter !!
CREATE TABLE user_manager (
                              id_user INT AUTO_INCREMENT PRIMARY KEY,
                              username VARCHAR(100) NOT NULL UNIQUE,
                              password_hash VARCHAR(255) NOT NULL,
                              role VARCHAR(50) NOT NULL,
                              actif BOOLEAN NOT NULL DEFAULT TRUE,
                              date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE audit_logs (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            idUser INT NULL,
                            action VARCHAR(100) NOT NULL,
                            log_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            ip_address VARCHAR(45),
                            status VARCHAR(20) NOT NULL,

                            CONSTRAINT fk_audit_user
                                FOREIGN KEY (idUser)
                                    REFERENCES utilisateur(idUser)
                                    ON DELETE SET NULL
);
INSERT INTO audit_logs (idUser, action, ip_address, status)
VALUES (1, 'Connexion', '192.168.1.10', 'Succès');

INSERT INTO audit_logs (idUser, action, ip_address, status)
VALUES (NULL, 'Tentative Connexion', '192.168.1.20', 'Échec');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
