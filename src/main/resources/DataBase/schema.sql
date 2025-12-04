-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : lun. 27 oct. 2025 à 18:41
-- Version du serveur : 8.0.43
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;

--
-- Base de données : `cabinet_medical`
--

-- --------------------------------------------------------

--
-- Structure de la table `antecedent`
--

CREATE TABLE `antecedent` (
  `idAntecedent` int NOT NULL,
  `nom` varchar(150) DEFAULT NULL,
  `categorie` varchar(100) DEFAULT NULL,
  `idRisque` int DEFAULT NULL,
  `idPatient` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `assurance`
--

CREATE TABLE `assurance` (
  `idAssurance` int NOT NULL,
  `libelle` enum('cmss','cnops','ramed','privée','aucune') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `cabinetmedical`
--

CREATE TABLE `cabinetmedical` (
  `idCabinet` int NOT NULL,
  `nom` varchar(150) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `logo` varchar(150) DEFAULT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  `tel1` varchar(20) DEFAULT NULL,
  `tel2` varchar(20) DEFAULT NULL,
  `siteweb` varchar(100) DEFAULT NULL,
  `description` text,
  `instagram` varchar(100) DEFAULT NULL,
  `facebook` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `certificat`
--

CREATE TABLE `certificat` (
  `idCert` int NOT NULL,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `nature` varchar(200) DEFAULT NULL,
  `noteMedecin` text,
  `idDossier` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `charges`
--

CREATE TABLE `charges` (
  `idCharge` int NOT NULL,
  `titre` varchar(150) DEFAULT NULL,
  `description` text,
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
  `observationMedecin` text,
  `idDossier` int DEFAULT NULL,
  `idStatut` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE `facture` (
  `idFact` int NOT NULL,
  `total` double DEFAULT NULL,
  `totalpaye` double DEFAULT NULL,
  `reste` double DEFAULT NULL,
  `statut` enum('payee','non payee','en attente','annulé') DEFAULT NULL,
  `dateFact` datetime DEFAULT NULL,
  `idSF` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `forme`
--

CREATE TABLE `forme` (
  `idForme` int NOT NULL,
  `libelle` enum('Comprimé','Gelule','Sirop','Pommade','Injection') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `medecin`
--

CREATE TABLE `medecin` (
  `idUser` int NOT NULL,
  `specialite` varchar(100) DEFAULT NULL,
  `agendaMensuel` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `medicament`
--

CREATE TABLE `medicament` (
  `idMed` int NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `laboratoire` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `remboursable` tinyint(1) DEFAULT NULL,
  `prixUnit` double DEFAULT NULL,
  `description` text,
  `idForme` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `niveaurisque`
--

CREATE TABLE `niveaurisque` (
  `idRisque` int NOT NULL,
  `libelle` enum('Faible','Modéré','Dangereux','Très dangereux') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `nom` varchar(100) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `idSexe` int DEFAULT NULL,
  `idAssurance` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `prescription`
--

CREATE TABLE `prescription` (
  `idPr` int NOT NULL,
  `quantite` int DEFAULT NULL,
  `frequence` varchar(50) DEFAULT NULL,
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
  `motif` varchar(150) DEFAULT NULL,
  `noteMedecin` text,
  `idPatient` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `revenus`
--

CREATE TABLE `revenus` (
  `idRev` int NOT NULL,
  `type` varchar(100) DEFAULT NULL,
  `description` text,
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
  `libelle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `secretaire`
--

CREATE TABLE `secretaire` (
  `idUser` int NOT NULL,
  `numCNSS` varchar(30) DEFAULT NULL,
  `commission` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `sexe`
--

CREATE TABLE `sexe` (
  `idSexe` int NOT NULL,
  `libelle` enum('homme','femme') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `situationfinanciere`
--

CREATE TABLE `situationfinanciere` (
  `idSF` int NOT NULL,
  `totalActes` double DEFAULT NULL,
  `totalPaye` double DEFAULT NULL,
  `credit` double DEFAULT NULL,
  `statut` enum('payee','non payee','en attente','annulé') DEFAULT NULL,
  `enPromo` enum('Oui','Non') DEFAULT NULL,
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
  `libelle` enum('En cours','Terminé','Annulé') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `idUser` int NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  `cin` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `idSexe` int DEFAULT NULL,
  `login` varchar(50) DEFAULT NULL,
  `motdepasse` varchar(255) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL,
  `idRole` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `antecedent`
--
ALTER TABLE `antecedent`
  ADD PRIMARY KEY (`idAntecedent`),
  ADD KEY `idRisque` (`idRisque`),
  ADD KEY `idPatient` (`idPatient`);

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
  ADD KEY `idDossier` (`idDossier`);

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
  MODIFY `idAntecedent` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `assurance`
--
ALTER TABLE `assurance`
  MODIFY `idAssurance` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `cabinetmedical`
--
ALTER TABLE `cabinetmedical`
  MODIFY `idCabinet` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `certificat`
--
ALTER TABLE `certificat`
  MODIFY `idCert` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `charges`
--
ALTER TABLE `charges`
  MODIFY `idCharge` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `consultation`
--
ALTER TABLE `consultation`
  MODIFY `idConsult` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `dossiermedical`
--
ALTER TABLE `dossiermedical`
  MODIFY `idDossier` int NOT NULL AUTO_INCREMENT;

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
  MODIFY `idRisque` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ordonnance`
--
ALTER TABLE `ordonnance`
  MODIFY `idOrd` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `patient`
--
ALTER TABLE `patient`
  MODIFY `idPatient` int NOT NULL AUTO_INCREMENT;

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
  MODIFY `idRole` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `sexe`
--
ALTER TABLE `sexe`
  MODIFY `idSexe` int NOT NULL AUTO_INCREMENT;

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
  MODIFY `idStatut` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `idUser` int NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `antecedent`
--
ALTER TABLE `antecedent`
  ADD CONSTRAINT `antecedent_ibfk_1` FOREIGN KEY (`idRisque`) REFERENCES `niveaurisque` (`idRisque`),
  ADD CONSTRAINT `antecedent_ibfk_2` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Contraintes pour la table `certificat`
--
ALTER TABLE `certificat`
  ADD CONSTRAINT `certificat_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`);

--
-- Contraintes pour la table `charges`
--
ALTER TABLE `charges`
  ADD CONSTRAINT `charges_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`);

--
-- Contraintes pour la table `consultation`
--
ALTER TABLE `consultation`
  ADD CONSTRAINT `consultation_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`),
  ADD CONSTRAINT `consultation_ibfk_2` FOREIGN KEY (`idStatut`) REFERENCES `statutconsultation` (`idStatut`);

--
-- Contraintes pour la table `dossiermedical`
--
ALTER TABLE `dossiermedical`
  ADD CONSTRAINT `dossiermedical_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`),
  ADD CONSTRAINT `dossiermedical_ibfk_2` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`);

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`idSF`) REFERENCES `situationfinanciere` (`idSF`);

--
-- Contraintes pour la table `medecin`
--
ALTER TABLE `medecin`
  ADD CONSTRAINT `medecin_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`);

--
-- Contraintes pour la table `medicament`
--
ALTER TABLE `medicament`
  ADD CONSTRAINT `medicament_ibfk_1` FOREIGN KEY (`idForme`) REFERENCES `forme` (`idForme`);

--
-- Contraintes pour la table `ordonnance`
--
ALTER TABLE `ordonnance`
  ADD CONSTRAINT `ordonnance_ibfk_1` FOREIGN KEY (`idDossier`) REFERENCES `dossiermedical` (`idDossier`);

--
-- Contraintes pour la table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`),
  ADD CONSTRAINT `patient_ibfk_2` FOREIGN KEY (`idAssurance`) REFERENCES `assurance` (`idAssurance`);

--
-- Contraintes pour la table `prescription`
--
ALTER TABLE `prescription`
  ADD CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`idOrd`) REFERENCES `ordonnance` (`idOrd`),
  ADD CONSTRAINT `prescription_ibfk_2` FOREIGN KEY (`idMed`) REFERENCES `medicament` (`idMed`);

--
-- Contraintes pour la table `rdv`
--
ALTER TABLE `rdv`
  ADD CONSTRAINT `rdv_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Contraintes pour la table `revenus`
--
ALTER TABLE `revenus`
  ADD CONSTRAINT `revenus_ibfk_1` FOREIGN KEY (`idCabinet`) REFERENCES `cabinetmedical` (`idCabinet`);

--
-- Contraintes pour la table `secretaire`
--
ALTER TABLE `secretaire`
  ADD CONSTRAINT `secretaire_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`idUser`);

--
-- Contraintes pour la table `situationfinanciere`
--
ALTER TABLE `situationfinanciere`
  ADD CONSTRAINT `situationfinanciere_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Contraintes pour la table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idUser`),
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`idSecretaire`) REFERENCES `secretaire` (`idUser`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`),
  ADD CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`idSexe`) REFERENCES `sexe` (`idSexe`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
