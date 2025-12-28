package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.*;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.CabinetMedical.impl.CabinetMedicaleImpl;
import ma.prodenta.repository.modules.RendezVous.fileBase_implementation.RDVDAOImpl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.assurance.implement.Assurance_impl;
import ma.prodenta.repository.modules.certificat.impl.CertificatDaoimpl;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.repository.modules.medcin.medcin_impl.MedecinDaoimpl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.role.impl.Role_impl;
import ma.prodenta.repository.modules.secretaire.impl.SecretaireDaoimpl;
import ma.prodenta.repository.modules.sexe.impl.Sexe_impl;
import ma.prodenta.repository.modules.staff.impl.StaffDaoImpl;
import ma.prodenta.repository.modules.statistiques.fileBase_implementation.ChargesDAOImpl;
import ma.prodenta.repository.modules.statistiques.fileBase_implementation.RevenusDAOImpl;
import ma.prodenta.repository.modules.user.implementation.UserImpl;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.statut_consultation.impl.Statut_consultation_impl;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestRepo1 {
    public static TestResult insertProcess() throws Exception {
        /// Cabinet medical
        CabinetMedicaleImpl cabinetMedical = new CabinetMedicaleImpl();
        CabinetMedical cabinetMedical1 = new CabinetMedical();
        cabinetMedical1.setIdCabinet(1);
        cabinetMedical1.setAdresse("Adresse");
        cabinetMedical1.setNom("Nom");
        cabinetMedical1.setEmail("email");
        cabinetMedical1.setLogo("logo");
        cabinetMedical1.setFacebook("facebook");
        cabinetMedical1.setDescription("Cabinet description");
        cabinetMedical1.setSiteweb("siteweb");
        cabinetMedical1.setTel1("tel1");
        cabinetMedical1.setTel2("tel2");
        cabinetMedical1.setInstagram("instagram");
        cabinetMedical.create(cabinetMedical1);
        Integer a=cabinetMedical.get_last_id();
        System.out.println(cabinetMedical.findById(a));
        /// /////////////charge
        ChargesDAOImpl chargesDAO = new ChargesDAOImpl();
        Charges charges = new Charges();
        charges.setIdCharge(2L);
        charges.setDateCharge(new Date());
        charges.setDescription("Cabinet description");
        charges.setIdCabinet(a);
        charges.setMontant(1000.00);
        charges.setTitre("Cabinet description");
        chargesDAO.create(charges);
        Long b = chargesDAO.get_last_id();
        System.out.println(chargesDAO.findById(b));
        /////////////revenus
        RevenusDAOImpl  revenusDAO = new RevenusDAOImpl();
        Revenus revenus = new Revenus();
        revenus.setDateRev(new Date());
        revenus.setDescription("Cabinet description");
        revenus.setIdCabinet(a);
        revenus.setMontant(1000.00);
        revenus.setIdRev(1L);
        revenusDAO.create(revenus);
        Long d = revenusDAO.get_last_id();
        System.out.println(revenusDAO.findById(d));
        /// /////////////////role
        Role_impl role = new Role_impl();
        Role role1 = new Role();
        role1.setIdRole(1);
        role1.setLibelle("test");
        role.create(role1);
        Integer f=role.get_last_id();
        System.out.println(role.findById(f));
        /// /////////////Sexe
        Sexe_impl sexe = new Sexe_impl();
        Sexe_c sexe1 = new Sexe_c();
        sexe1.setId(1);
        sexe1.setLibelle("test1");
        sexe.create(sexe1);
        Integer g=sexe.get_last_id();
        System.out.println(sexe.findById(g));
        /// /////////utilisateur
        UserImpl user = new UserImpl();
        Utilisateur user1 = new Utilisateur();
        user1.setNom("test1");
        user1.setEmail("test1");
        user1.setTel("test1");
        user1.setAdresse("test1");
        user1.setCin("test12");
        user1.setLogin("&5d78");
        LocalDate date=LocalDate.now();
        user1.setDateNaissance(date);
        user1.setIdSexe(g);
        user1.setIdRole(f);
        LocalDate r=LocalDate.now();
        user1.setLastLoginDate(r.atStartOfDay());
        user1.setMotdepasse("test1");
        user.create(user1);
        Integer h = user.get_last_id();
        System.out.println(user.findById(h));
        /// /////////medecin
        MedecinDaoimpl medecinDAO = new MedecinDaoimpl();
        Medecin medecin = new Medecin();
        medecin.setIdUser(h);
        medecin.setSpecialite("test1");
        medecin.setAgendaMensuel("test1");
        medecinDAO.create(medecin);
        System.out.println(medecinDAO.findById(h));
        /// /////////secretaire
        SecretaireDaoimpl secretaireDAO = new SecretaireDaoimpl();
        Secretaire secretaire = new Secretaire();
        secretaire.setIdUser(h);
        secretaire.setCommission(1000.00);
        secretaire.setNumCNSS("4242");
        secretaireDAO.create(secretaire);
        System.out.println(secretaireDAO.findById(h));
        /// ///////////staff
        StaffDaoImpl staffDAO = new StaffDaoImpl();
        Staff staff = new Staff();
        staff.setIdStaff(774884);
        staff.setPrime(1000.00);
        staff.setSalaire(4500.00);
        Date da=new Date();
        staff.setDateRecrutement(da);
        staff.setIdMedecin(h);
        staff.setIdSecretaire(f);
        staff.setSoldeConge(500);
        staffDAO.create(staff);
        Integer i=staffDAO.get_last_id();
        System.out.println(staffDAO.findById(i));
        //////////////patient
        Antecedent antecedent = new Antecedent();
        antecedent.setNom("test1");
        antecedent.setCategorie("dakchi");
        antecedent.setNiveauRisque(NiveauRisque.Dangereux);
        Antecedent_impl antecedentDao = new Antecedent_impl();
        boolean antecedentCreated = antecedentDao.create(antecedent);
        if (!antecedentCreated) {
            System.out.println("Erreur lors de la création de l'antécédent");
        } else {
            System.out.println("Antécédent créé avec l'ID : " + antecedent.getIdAntecedent());
        }

        List<Antecedent> antecedents = new ArrayList<>();
        antecedents.add(antecedent);
        Patient patient = new Patient();
        patient.setAntecedents(antecedents);
        patient.setNom("test1");
        patient.setEmail("test1");
        patient.setTelephone("0658516");
        patient.setAdresse("test1");
        patient.setSexe(Sexe.Homme);
        patient.setDateNaissance(date);
        patient.setAssurance(Assurance.CNSS);
        patient.setPrenom("test1");
        // Ne pas définir l'ID manuellement, il sera généré automatiquement
        Patient_impl patientDao = new Patient_impl();
        boolean patientCreated = patientDao.create(patient);
        if (!patientCreated) {
            System.out.println("Erreur lors de la création du patient");
        } else {
            // L'ID du patient est maintenant défini dans l'objet patient après la création
            System.out.println("Patient créé avec l'ID : " + patient.getId());
            System.out.println(patientDao.findById(patient.getId()));
        }

        //////////////DossierMedical
        Dossier_medical_impl dossierDao = new Dossier_medical_impl();
        DossierMedical dossier = new DossierMedical();
        dossier.setIdPatient(patient.getId()); // Utiliser l'ID du patient créé
        dossier.setIdMedecin(h); // Utiliser l'ID du médecin créé
        boolean dossierCreated = dossierDao.create(dossier);
        Integer idDossier = null;
        if (!dossierCreated) {
            System.out.println("Erreur lors de la création du dossier médical");
        } else {
            idDossier = dossierDao.get_last_id();
            System.out.println("Dossier médical créé avec l'ID : " + idDossier);
            System.out.println(dossierDao.findById(idDossier));
        }

        //////////////StatutConsultation - utiliser un statut existant (1=En cours)
        Statut_consultation_impl statutDao = new Statut_consultation_impl();
        Staut_consultation statut = statutDao.findById(1); // Utiliser le statut "En cours" existant
        if (statut == null) {
            // Si le statut n'existe pas, en créer un
            statut = new Staut_consultation();
            statut.setLibelle("En cours");
            statutDao.create(statut);
            statut = statutDao.findBYnom("En cours");
        }
        Integer idStatut = statut.getId();
        System.out.println("Statut consultation utilisé : " + statut.getLibelle() + " (ID: " + idStatut + ")");

        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        RDV rdv = new RDV();
        Date dateRDV = new Date(104, 8, 9);
        Time heure = Time.valueOf("14:30:45");
        rdv.setIddossier(idDossier);
        rdv.setDateRDV(dateRDV);
        rdv.setHeure(heure);
        rdv.setMotif("Trois dents cassées");
        rdv.setNoteMedecin("Besoin d'une intervention rapide");

        rdvDAO.create(rdv);

        int idRdvGenere = rdvDAO.get_last_id();
        RDV rdvInserted = rdvDAO.findById(idRdvGenere);
        System.out.println("RDV inséré : " + rdvInserted);

        Consultation consultation = new Consultation();
        consultation.setDateConsult(dateRDV);
        consultation.setIdDossier(idDossier);
        consultation.setObservationMedecin("Besoin d'une intervention rapide");
        consultation.setId_medecin(h);
        consultation.setIdStatut(idStatut);
        consultation.setId_rdv(idRdvGenere);

        consultationDAO.create(consultation);


        int idConsultGenere = consultationDAO.last_id();
        Consultation consultationInserted = consultationDAO.findById(idConsultGenere);
        System.out.println("Consultation insérée : " + consultationInserted);


        Certificat cert1 = Certificat.builder()
                .dateDebut(new Date())
                .dateFin(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .nature("Repos maladie")
                .noteMedecin("Patient à reposer")
                .idDossier(idDossier) // Utiliser l'ID du dossier créé
                .idConsult(idConsultGenere) // Utiliser l'ID de la consultation créée
                .build();

        certDAO.create(cert1);


        int idCertGenere = certDAO.get_last_id();
        Certificat certInserted = certDAO.findById(idCertGenere);
        System.out.println("Certificat inséré : " + certInserted);

        // Retourner les objets créés pour les utiliser dans update et delete
        return new TestResult(rdvInserted, consultationInserted, certInserted);
    }

    // Classe interne pour stocker les résultats de l'insertion
    static class TestResult {
        RDV rdv;
        Consultation consultation;
        Certificat certificat;

        TestResult(RDV rdv, Consultation consultation, Certificat certificat) {
            this.rdv = rdv;
            this.consultation = consultation;
            this.certificat = certificat;
        }
    }

    public static void updateProcess(Certificat cert, RDV rdv, Consultation consultation) throws Exception {
        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        // Affichage avant update
        System.out.println("\n=== AVANT UPDATE ===");
        System.out.println("RDV : " + rdv);
        System.out.println("Consultation : " + consultation);
        System.out.println("Certificat : " + cert);

        // Vérifier les relations avant update
        System.out.println("\n--- Vérification des relations AVANT update ---");
        System.out.println("RDV.idDossier = " + rdv.getIddossier());
        System.out.println("Consultation.idDossier = " + consultation.getIdDossier());
        System.out.println("Consultation.id_rdv = " + consultation.getId_rdv());
        System.out.println("Consultation.id_medecin = " + consultation.getId_medecin());
        System.out.println("Certificat.idDossier = " + cert.getIdDossier());
        System.out.println("Certificat.idConsult = " + cert.getIdConsult());

        // Mise à jour
        rdvDAO.update(rdv);
        consultationDAO.update(consultation);
        certDAO.update(cert);

        // Récupérer les objets mis à jour
        RDV rdvUpdated = rdvDAO.findById(rdv.getIdRDV());
        Consultation consultationUpdated = consultationDAO.findById(consultation.getIdConsult());
        Certificat certUpdated = certDAO.findById(cert.getIdCert());

        // Affichage après update
        System.out.println("\n=== APRÈS UPDATE ===");
        System.out.println("RDV : " + rdvUpdated);
        System.out.println("Consultation : " + consultationUpdated);
        System.out.println("Certificat : " + certUpdated);

        // Vérifier que les relations sont toujours cohérentes après update
        System.out.println("\n--- Vérification des relations APRÈS update (CASCADE) ---");
        boolean relationsOK = true;

        if (!rdvUpdated.getIddossier().equals(consultationUpdated.getIdDossier())) {
            System.out.println("❌ ERREUR: RDV.idDossier (" + rdvUpdated.getIddossier() +
                    ") != Consultation.idDossier (" + consultationUpdated.getIdDossier() + ")");
            relationsOK = false;
        } else {
            System.out.println("✓ RDV.idDossier = Consultation.idDossier = " + rdvUpdated.getIddossier());
        }

        if (!rdvUpdated.getIdRDV().equals(consultationUpdated.getId_rdv())) {
            System.out.println("❌ ERREUR: RDV.idRDV (" + rdvUpdated.getIdRDV() +
                    ") != Consultation.id_rdv (" + consultationUpdated.getId_rdv() + ")");
            relationsOK = false;
        } else {
            System.out.println("✓ RDV.idRDV = Consultation.id_rdv = " + rdvUpdated.getIdRDV());
        }

        if (!consultationUpdated.getIdDossier().equals(certUpdated.getIdDossier())) {
            System.out.println("❌ ERREUR: Consultation.idDossier (" + consultationUpdated.getIdDossier() +
                    ") != Certificat.idDossier (" + certUpdated.getIdDossier() + ")");
            relationsOK = false;
        } else {
            System.out.println("✓ Consultation.idDossier = Certificat.idDossier = " + consultationUpdated.getIdDossier());
        }

        if (!consultationUpdated.getIdConsult().equals(certUpdated.getIdConsult())) {
            System.out.println("❌ ERREUR: Consultation.idConsult (" + consultationUpdated.getIdConsult() +
                    ") != Certificat.idConsult (" + certUpdated.getIdConsult() + ")");
            relationsOK = false;
        } else {
            System.out.println("✓ Consultation.idConsult = Certificat.idConsult = " + consultationUpdated.getIdConsult());
        }

        if (relationsOK) {
            System.out.println("\n✅ Toutes les relations en cascade sont cohérentes après l'update !");
        } else {
            System.out.println("\n❌ Certaines relations ne sont pas cohérentes !");
        }
    }

    public static void deleteProcessCascade(RDV rdv, Consultation consultation, Certificat cert) throws Exception {
        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        System.out.println("\n=== TEST DE SUPPRESSION EN CASCADE ===");
        System.out.println("RDV à supprimer : " + rdv);
        System.out.println("Consultation liée : " + consultation);
        System.out.println("Certificat lié : " + cert);

        // Vérifier que les entités existent avant suppression
        System.out.println("\n--- Vérification AVANT suppression ---");
        RDV rdvAvant = rdvDAO.findById(rdv.getIdRDV());
        Consultation consultAvant = consultationDAO.findById(consultation.getIdConsult());
        Certificat certAvant = certDAO.findById(cert.getIdCert());

        System.out.println("RDV existe ? " + (rdvAvant != null ? "OUI (ID: " + rdvAvant.getIdRDV() + ")" : "NON"));
        System.out.println("Consultation existe ? " + (consultAvant != null ? "OUI (ID: " + consultAvant.getIdConsult() + ")" : "NON"));
        System.out.println("Certificat existe ? " + (certAvant != null ? "OUI (ID: " + certAvant.getIdCert() + ")" : "NON"));

        // Compter les relations
        List<Consultation> consultations = consultationDAO.findByRdv(rdv.getIdRDV());
        System.out.println("Nombre de consultations liées au RDV : " + consultations.size());

        for (Consultation c : consultations) {
            List<Certificat> certs = certDAO.findByConsultation(c.getIdConsult());
            System.out.println("Nombre de certificats liés à la consultation " + c.getIdConsult() + " : " + certs.size());
        }

        // Suppression en cascade (dans l'ordre : Certificat -> Consultation -> RDV)
        System.out.println("\n--- Suppression en cascade ---");

        // 1. Supprimer tous les certificats liés aux consultations
        for (Consultation c : consultations) {
            List<Certificat> certs = certDAO.findByConsultation(c.getIdConsult());
            for (Certificat certToDelete : certs) {
                boolean deleted = certDAO.delete(certToDelete);
                if (deleted) {
                    System.out.println("✓ Certificat supprimé : ID=" + certToDelete.getIdCert());
                } else {
                    System.out.println("❌ Erreur lors de la suppression du certificat ID=" + certToDelete.getIdCert());
                }
            }
        }

        // 2. Supprimer toutes les consultations liées au RDV
        for (Consultation c : consultations) {
            boolean deleted = consultationDAO.delete(c);
            if (deleted) {
                System.out.println("✓ Consultation supprimée : ID=" + c.getIdConsult());
            } else {
                System.out.println("❌ Erreur lors de la suppression de la consultation ID=" + c.getIdConsult());
            }
        }

        // 3. Supprimer le RDV
        boolean rdvDeleted = rdvDAO.deleteById(rdv.getIdRDV());
        if (rdvDeleted) {
            System.out.println("✓ RDV supprimé : ID=" + rdv.getIdRDV());
        } else {
            System.out.println("✓ RDV supprimé (ou déjà supprimé) : ID=" + rdv.getIdRDV());
        }

        // Vérification après suppression
        System.out.println("\n--- Vérification APRÈS suppression (CASCADE) ---");
        RDV rdvApres = rdvDAO.findById(rdv.getIdRDV());
        Consultation consultApres = consultationDAO.findById(consultation.getIdConsult());
        Certificat certApres = certDAO.findById(cert.getIdCert());

        boolean cascadeOK = true;

        // Vérifier si le RDV existe (null ou ID null signifie qu'il n'existe pas)
        if (rdvApres != null && rdvApres.getIdRDV() != null) {
            System.out.println("❌ ERREUR: Le RDV existe encore après suppression ! ID=" + rdvApres.getIdRDV());
            cascadeOK = false;
        } else {
            System.out.println("✓ RDV correctement supprimé");
        }

        // Vérifier si la Consultation existe (null ou ID null signifie qu'elle n'existe pas)
        if (consultApres != null && consultApres.getIdConsult() != null) {
            System.out.println("❌ ERREUR: La Consultation existe encore après suppression ! ID=" + consultApres.getIdConsult());
            cascadeOK = false;
        } else {
            System.out.println("✓ Consultation correctement supprimée");
        }

        // Vérifier si le Certificat existe (null ou ID null signifie qu'il n'existe pas)
        if (certApres != null && certApres.getIdCert() != null) {
            System.out.println("❌ ERREUR: Le Certificat existe encore après suppression ! ID=" + certApres.getIdCert());
            cascadeOK = false;
        } else {
            System.out.println("✓ Certificat correctement supprimé");
        }

        // Vérifier qu'il n'y a plus de consultations liées au RDV
        List<Consultation> consultationsApres = consultationDAO.findByRdv(rdv.getIdRDV());
        if (!consultationsApres.isEmpty()) {
            System.out.println("❌ ERREUR: Il reste " + consultationsApres.size() + " consultation(s) liée(s) au RDV supprimé !");
            cascadeOK = false;
        } else {
            System.out.println("✓ Aucune consultation liée au RDV supprimé");
        }

        if (cascadeOK) {
            System.out.println("\n✅ La suppression en cascade a fonctionné correctement !");
        } else {
            System.out.println("\n❌ La suppression en cascade n'a pas fonctionné correctement !");
        }
    }

     static void main() throws Exception {

        // --- 1. INSERTION ---
        System.out.println("=== INSERTION ===");
        TestResult result = insertProcess();

        // Utiliser les objets créés directement pour update et delete
        RDV rdv = result.rdv;
        Consultation consultation = result.consultation;
        Certificat cert = result.certificat;

        // --- 2. UPDATE ---
        System.out.println("\n=== UPDATE ===");
        // Exemple de modification - les clés étrangères restent les mêmes (créées dans insertProcess)
        rdv.setMotif("Changement du motif");
        consultation.setObservationMedecin("Observation mise à jour");
        cert.setNoteMedecin("Note médecin mise à jour");

        updateProcess(cert, rdv, consultation);

        // --- 3. DELETE ---
        System.out.println("\n=== DELETE ===");
        // Utiliser les objets créés pour tester la suppression en cascade
        deleteProcessCascade(rdv, consultation, cert);

        // Vérification que tout a été supprimé
        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        RDV rdvCheck = rdvDAO.findById(rdv.getIdRDV());
        Consultation consultCheck = consultationDAO.findById(consultation.getIdConsult());
        Certificat certCheck = certDAO.findById(cert.getIdCert());

        System.out.println("\n=== VÉRIFICATION APRÈS DELETE ===");
        // Vérifier si les objets existent vraiment (ID null signifie qu'ils n'existent pas)
        boolean rdvExiste = rdvCheck != null && rdvCheck.getIdRDV() != null;
        boolean consultExiste = consultCheck != null && consultCheck.getIdConsult() != null;
        boolean certExiste = certCheck != null && certCheck.getIdCert() != null;

        System.out.println("RDV existant ? " + (rdvExiste ? "OUI (ID: " + rdvCheck.getIdRDV() + ")" : "NON"));
        System.out.println("Consultation existante ? " + (consultExiste ? "OUI (ID: " + consultCheck.getIdConsult() + ")" : "NON"));
        System.out.println("Certificat existant ? " + (certExiste ? "OUI (ID: " + certCheck.getIdCert() + ")" : "NON"));

        if (!rdvExiste && !consultExiste && !certExiste) {
            System.out.println("\n✅ Tous les éléments ont été correctement supprimés en cascade !");
        } else {
            System.out.println("\n❌ Certains éléments existent encore après la suppression !");
        }

    }


}

