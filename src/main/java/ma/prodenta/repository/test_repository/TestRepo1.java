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
import ma.prodenta.repository.modules.auth.implementation.AuthDaoImpl;
import ma.prodenta.repository.modules.userManager.implementation.UserManagerRepositoryImpl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.common.util.PasswordUtil;

import java.sql.Connection;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TestRepo1 {

    public static void testAuthRepository() throws Exception {
        System.out.println("\n========== TEST AUTH REPOSITORY ==========");

        AuthDaoImpl authDao = new AuthDaoImpl();

        // Test 1: Création d'un utilisateur pour auth
        System.out.println("\n--- Test 1: Création utilisateur ---");
        Utilisateur testUser = new Utilisateur();
        testUser.setNom("Test Auth User");
        testUser.setEmail("authuser@test.com");
        testUser.setAdresse("123 Auth Street");
        testUser.setCin("AUTH12345");
        testUser.setTel("0612345678");
        testUser.setLogin("authtestuser");
        testUser.setMotdepasse(PasswordUtil.hashPassword("SecurePass123"));
        testUser.setDateNaissance(LocalDate.of(1990, 5, 15));
        testUser.setIdSexe(1);
        testUser.setIdRole(1);

        boolean created = authDao.create(testUser);
        System.out.println("✓ Utilisateur créé: " + created);

        // Test 2: findByLogin
        System.out.println("\n--- Test 2: findByLogin ---");
        Optional<Utilisateur> foundByLogin = authDao.findByLogin("authtestuser");
        if (foundByLogin.isPresent()) {
            System.out.println("✓ Trouvé par login: " + foundByLogin.get().getNom());
        } else {
            System.out.println("✗ Non trouvé par login");
        }

        // Test 3: findByEmail
        System.out.println("\n--- Test 3: findByEmail ---");
        Optional<Utilisateur> foundByEmail = authDao.findByEmail("authuser@test.com");
        if (foundByEmail.isPresent()) {
            System.out.println("✓ Trouvé par email: " + foundByEmail.get().getNom());
        } else {
            System.out.println("✗ Non trouvé par email");
        }

        // Test 4: existsByLogin
        System.out.println("\n--- Test 4: existsByLogin ---");
        boolean exists = authDao.existsByLogin("authtestuser");
        System.out.println("✓ Login existe: " + exists);

        // Test 5: existsByEmail
        System.out.println("\n--- Test 5: existsByEmail ---");
        boolean emailExists = authDao.existsByEmail("authuser@test.com");
        System.out.println("✓ Email existe: " + emailExists);

        // Test 6: login (authenticate)
        System.out.println("\n--- Test 6: login (authentication) ---");
        Optional<Utilisateur> authenticated = authDao.login("authtestuser", PasswordUtil.hashPassword("SecurePass123"));
        if (authenticated.isPresent()) {
            System.out.println("✓ Authentification réussie pour: " + authenticated.get().getLogin());
        } else {
            System.out.println("✗ Authentification échouée");
        }

        // Test 7: count
        System.out.println("\n--- Test 7: count ---");
        long count = authDao.count();
        System.out.println("✓ Nombre total d'utilisateurs: " + count);

        // Test 8: findAll
        System.out.println("\n--- Test 8: findAll ---");
        List<Utilisateur> allUsers = authDao.findAll();
        System.out.println("✓ Liste de tous les utilisateurs: " + allUsers.size() + " utilisateur(s)");

        // Test 9: update
        if (foundByLogin.isPresent()) {
            System.out.println("\n--- Test 9: update ---");
            Utilisateur userToUpdate = foundByLogin.get();
            String oldName = userToUpdate.getNom();
            userToUpdate.setNom("Test Auth User UPDATED");
            userToUpdate.setAdresse("456 Updated Street");
            authDao.update(userToUpdate);

            Optional<Utilisateur> updated = authDao.findByLogin("authtestuser");
            if (updated.isPresent()) {
                System.out.println("✓ Nom avant: " + oldName);
                System.out.println("✓ Nom après: " + updated.get().getNom());
            }
        }

        // Test 10: findById
        if (foundByLogin.isPresent()) {
            System.out.println("\n--- Test 10: findById ---");
            Integer userId = foundByLogin.get().getIdUser();
            Utilisateur foundById = authDao.findById(userId);
            if (foundById != null) {
                System.out.println("✓ Trouvé par ID " + userId + ": " + foundById.getNom());
            } else {
                System.out.println("✗ Non trouvé par ID");
            }
        }

        // Test 11: delete
        if (foundByLogin.isPresent()) {
            System.out.println("\n--- Test 11: delete ---");
            Integer userIdToDelete = foundByLogin.get().getIdUser();
            boolean deleted = authDao.deleteById(userIdToDelete);
            System.out.println("✓ Utilisateur supprimé: " + deleted);

            // Vérification de la suppression
            Utilisateur shouldBeNull = authDao.findById(userIdToDelete);
            System.out.println("✓ Vérification suppression: " + (shouldBeNull == null ? "OK" : "ERREUR"));
        }

        System.out.println("\n========== FIN TEST AUTH REPOSITORY ==========");
    }

    public static void testUserManagerRepository() throws Exception {
        System.out.println("\n========== TEST USERMANAGER REPOSITORY ==========");

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl userManagerRepo = new UserManagerRepositoryImpl(conn);

            // Test 1: Création d'un UserManager
            System.out.println("\n--- Test 1: Création UserManager ---");
            UserManager testUserManager = new UserManager();
            testUserManager.setUsername("testmanager01");
            testUserManager.setPasswordHash(PasswordUtil.hashPassword("ManagerPass123"));
            testUserManager.setRole("ADMIN");
            testUserManager.setActif(true);
            testUserManager.setDateCreation(new Date());

            UserManager saved = userManagerRepo.save(testUserManager);
            System.out.println("✓ UserManager créé avec ID: " + saved.getIdUser());
            System.out.println("  Username: " + saved.getUsername());
            System.out.println("  Role: " + saved.getRole());
            System.out.println("  Actif: " + saved.getActif());

            // Test 2: findById
            System.out.println("\n--- Test 2: findById ---");
            UserManager foundById = userManagerRepo.findById(saved.getIdUser());
            if (foundById != null) {
                System.out.println("✓ Trouvé par ID " + saved.getIdUser() + ": " + foundById.getUsername());
            } else {
                System.out.println("✗ Non trouvé par ID");
            }

            // Test 3: findByUsername
            System.out.println("\n--- Test 3: findByUsername ---");
            UserManager foundByUsername = userManagerRepo.findByUsername("testmanager01");
            if (foundByUsername != null) {
                System.out.println("✓ Trouvé par username: " + foundByUsername.getUsername());
                System.out.println("  Role: " + foundByUsername.getRole());
            } else {
                System.out.println("✗ Non trouvé par username");
            }

            // Test 4: findAll
            System.out.println("\n--- Test 4: findAll ---");
            List<UserManager> allUsers = userManagerRepo.findAll();
            System.out.println("✓ Nombre total de UserManagers: " + allUsers.size());
            for (UserManager um : allUsers) {
                System.out.println("  - " + um.getUsername() + " (" + um.getRole() + ") - Actif: " + um.getActif());
            }

            // Test 5: update
            System.out.println("\n--- Test 5: update ---");
            String oldRole = saved.getRole();
            Boolean oldActif = saved.getActif();

            saved.setRole("SUPER_ADMIN");
            saved.setActif(false);
            UserManager updated = userManagerRepo.update(saved);

            System.out.println("✓ Role avant: " + oldRole + " → après: " + updated.getRole());
            System.out.println("✓ Actif avant: " + oldActif + " → après: " + updated.getActif());

            // Test 6: Réactivation
            System.out.println("\n--- Test 6: Réactivation ---");
            updated.setActif(true);
            UserManager reactivated = userManagerRepo.update(updated);
            System.out.println("✓ UserManager réactivé: " + reactivated.getActif());

            // Test 7: Créer un deuxième UserManager pour tester la liste
            System.out.println("\n--- Test 7: Création d'un deuxième UserManager ---");
            UserManager testUserManager2 = new UserManager();
            testUserManager2.setUsername("testmanager02");
            testUserManager2.setPasswordHash(PasswordUtil.hashPassword("Pass123"));
            testUserManager2.setRole("MEDECIN");
            testUserManager2.setActif(false);
            testUserManager2.setDateCreation(new Date());

            UserManager saved2 = userManagerRepo.save(testUserManager2);
            System.out.println("✓ Deuxième UserManager créé: " + saved2.getUsername());

            // Test 8: Liste finale
            System.out.println("\n--- Test 8: Liste finale des UserManagers ---");
            List<UserManager> finalList = userManagerRepo.findAll();
            System.out.println("✓ Total: " + finalList.size() + " UserManager(s)");
            for (UserManager um : finalList) {
                System.out.println("  - ID:" + um.getIdUser() + " | " + um.getUsername() +
                        " | Role:" + um.getRole() + " | Actif:" + um.getActif());
            }

            // Test 9: delete premier UserManager
            System.out.println("\n--- Test 9: delete ---");
            userManagerRepo.delete(saved.getIdUser());
            UserManager deletedCheck = userManagerRepo.findById(saved.getIdUser());
            System.out.println("✓ Premier UserManager supprimé: " + (deletedCheck == null));

            // Test 10: delete deuxième UserManager
            System.out.println("\n--- Test 10: Nettoyage ---");
            userManagerRepo.delete(saved2.getIdUser());
            UserManager deletedCheck2 = userManagerRepo.findById(saved2.getIdUser());
            System.out.println("✓ Deuxième UserManager supprimé: " + (deletedCheck2 == null));

        }

        System.out.println("\n========== FIN TEST USERMANAGER REPOSITORY ==========");
    }

    public static TestResult insertProcess() throws Exception {
        /// Cabinet medical
        //////////////////////////
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
        Integer c = b.intValue();
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
        Integer e = b.intValue();
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

        /// ///////////////////////RDV, Consultation, Certificat
        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        // Créer RDV avec le dossier créé
        RDV rdv = new RDV();
        Date dateRDV = new Date(104, 8, 9);
        Time heure = Time.valueOf("14:30:45");
        rdv.setIddossier(idDossier); // Utiliser l'ID du dossier créé
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
        consultation.setIdDossier(idDossier); // Utiliser l'ID du dossier créé
        consultation.setObservationMedecin("Besoin d'une intervention rapide");
        consultation.setId_medecin(h); // Utiliser l'ID du médecin créé
        consultation.setIdStatut(idStatut); // Utiliser l'ID du statut
        consultation.setId_rdv(idRdvGenere); // Utiliser l'ID du RDV créé

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

        // Vérifier les relations après update
        System.out.println("\n--- Vérification des relations APRÈS update ---");
        System.out.println("RDV.idDossier = " + rdvUpdated.getIddossier());
        System.out.println("Consultation.idDossier = " + consultationUpdated.getIdDossier());
        System.out.println("Consultation.id_rdv = " + consultationUpdated.getId_rdv());
        System.out.println("Consultation.id_medecin = " + consultationUpdated.getId_medecin());
        System.out.println("Certificat.idDossier = " + certUpdated.getIdDossier());
        System.out.println("Certificat.idConsult = " + certUpdated.getIdConsult());
    }

    public static void deleteProcess(Certificat cert, RDV rdv, Consultation consultation) throws Exception {
        System.out.println("\n=== SUPPRESSION ===");

        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        // Delete certificat
        certDAO.deleteById(cert.getIdCert());
        System.out.println("Certificat supprimé : " + cert.getIdCert());

        // Delete consultation
        consultationDAO.deleteById(consultation.getIdConsult());
        System.out.println("Consultation supprimée : " + consultation.getIdConsult());

        // Delete RDV
        rdvDAO.deleteById(rdv.getIdRDV());
        System.out.println("RDV supprimé : " + rdv.getIdRDV());
    }

    public static void main(String[] args) {
        try {
            System.out.println("=== DÉMARRAGE DES TESTS ===\n");

            testAuthRepository();
            testUserManagerRepository();

            // Tests existants
            TestResult result = insertProcess();
            updateProcess(result.certificat, result.rdv, result.consultation);
            deleteProcess(result.certificat, result.rdv, result.consultation);

            System.out.println("\n=== TOUS LES TESTS TERMINÉS AVEC SUCCÈS ===");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\n=== ERREUR LORS DES TESTS ===");
        }
    }
}
