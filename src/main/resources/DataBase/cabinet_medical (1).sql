-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 04, 2025 at 04:49 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `acte`
--

CREATE TABLE `acte` (
  `id` int(11) NOT NULL,
  `categorie` varchar(30) DEFAULT NULL,
  `libelle` varchar(30) DEFAULT NULL,
  `prix_de_base` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(14, 'Epilepsie', 'Neurologique', 2),
(15, 'Hypertension légère', 'Cardiaque', 4);

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
  `idDossier` int(11) DEFAULT NULL
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
  `idStatut` int(11) DEFAULT NULL
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
  `idSF` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `forme`
--

CREATE TABLE `forme` (
  `idForme` int(11) NOT NULL,
  `libelle` enum('Comprimé','Gelule','Sirop','Pommade','Injection') NOT NULL
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
  `idDossier` int(11) DEFAULT NULL
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
(13, 'safiyeddine', '2024-10-03', 'Dahia', '0777181657', 1, 2, 'hachem', 'hezbollah'),
(14, 'safiyeddine', '2024-10-03', 'Dahia', '0777181657', 1, 2, 'hachem', 'hezbollah'),
(15, 'safiyeddine', '2024-10-03', 'Dahia', '0777181657', 1, 2, 'hachem', 'hezbollah'),
(16, 'safiyeddine', '2024-10-03', 'Dahia', '0777181657', 1, 2, 'hachem', 'hezbollah');

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
  `idPatient` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(2, 'Secretaire');

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
(2, 'sara', 'ilyasmoulragouba@gmail.com', 'rabat', 'Fjfajf', '0281938192', 2, 'salut', 'Pass@1234', '1995-09-20', NULL, 2);

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
  ADD KEY `idDossier` (`idDossier`);

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
  ADD KEY `idStatut` (`idStatut`);

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
  ADD KEY `idSF` (`idSF`);

--
-- Indexes for table `forme`
--
ALTER TABLE `forme`
  ADD PRIMARY KEY (`idForme`);

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
  ADD KEY `idDossier` (`idDossier`);

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
  ADD KEY `id_antecedent` (`id_antecedent`),
  ADD KEY `patient_antecedent_ibfk_1` (`id_patient`);

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
  ADD KEY `idPatient` (`idPatient`);

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
  MODIFY `idAntecedent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

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
  MODIFY `idConsult` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `dossiermedical`
--
ALTER TABLE `dossiermedical`
  MODIFY `idDossier` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `facture`
--
ALTER TABLE `facture`
  MODIFY `idFact` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `forme`
--
ALTER TABLE `forme`
  MODIFY `idForme` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `medicament`
--
ALTER TABLE `medicament`
  MODIFY `idMed` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `niveaurisque`
--
ALTER TABLE `niveaurisque`
  MODIFY `idRisque` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `ordonnance`
--
ALTER TABLE `ordonnance`
  MODIFY `idOrd` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `idPatient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `prescription`
--
ALTER TABLE `prescription`
  MODIFY `idPr` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rdv`
--
ALTER TABLE `rdv`
  MODIFY `idRDV` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `idStatut` int(11) NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `antecedent_ibfk_1` FOREIGN KEY (`idRisque`) REFERENCES `niveaurisque` (`idRisque`);

--
-- Constraints for table `certificat`
--
ALTER TABLE `certificat`
  ADD CONSTRAINT `certificat_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`);

--
-- Constraints for table `charges`
--
ALTER TABLE `charges`
  ADD CONSTRAINT `charges_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`);

--
-- Constraints for table `consultation`
--
ALTER TABLE `consultation`
  ADD CONSTRAINT `consultation_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`),
  ADD CONSTRAINT `consultation_ibfk_2` FOREIGN KEY (`idStatut`) REFERENCES `statutconsultation` (`idStatut`);

--
-- Constraints for table `dossiermedical`
--
ALTER TABLE `dossiermedical`
  ADD CONSTRAINT `dossiermedical_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`),
  ADD CONSTRAINT `dossiermedical_ibfk_2` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`);

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`idSF`) REFERENCES `situationfinanciere` (`idSF`);

--
-- Constraints for table `medecin`
--
ALTER TABLE `medecin`
  ADD CONSTRAINT `medecin_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`);

--
-- Constraints for table `medicament`
--
ALTER TABLE `medicament`
  ADD CONSTRAINT `medicament_ibfk_1` FOREIGN KEY (`idForme`) REFERENCES `forme` (`idForme`);

--
-- Constraints for table `ordonnance`
--
ALTER TABLE `ordonnance`
  ADD CONSTRAINT `ordonnance_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`);

--
-- Constraints for table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`),
  ADD CONSTRAINT `patient_ibfk_2` FOREIGN KEY (`idAssurance`) REFERENCES `assurance` (`idAssurance`);

--
-- Constraints for table `patient_antecedent`
--
ALTER TABLE `patient_antecedent`
  ADD CONSTRAINT `patient_antecedent_ibfk_1` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`idPatient`),
  ADD CONSTRAINT `patient_antecedent_ibfk_2` FOREIGN KEY (`id_antecedent`) REFERENCES `antecedent` (`idAntecedent`);

--
-- Constraints for table `prescription`
--
ALTER TABLE `prescription`
  ADD CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`idOrd`) REFERENCES `ordonnance` (`idOrd`),
  ADD CONSTRAINT `prescription_ibfk_2` FOREIGN KEY (`idMed`) REFERENCES `medicament` (`idMed`);

--
-- Constraints for table `rdv`
--
ALTER TABLE `rdv`
  ADD CONSTRAINT `rdv_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Constraints for table `revenus`
--
ALTER TABLE `revenus`
  ADD CONSTRAINT `revenus_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`);

--
-- Constraints for table `secretaire`
--
ALTER TABLE `secretaire`
  ADD CONSTRAINT `secretaire_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`);

--
-- Constraints for table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
  ADD CONSTRAINT `situationfinanciere_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`),
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`idSecretaire`) REFERENCES `secretaire` (`idUser`);

--
-- Constraints for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`),
  ADD CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
