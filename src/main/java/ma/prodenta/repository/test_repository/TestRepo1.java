package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Certificat;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.modules.RendezVous.fileBase_implementation.RDVDAOImpl;
import ma.prodenta.repository.modules.certificat.impl.CertificatDaoimpl;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class TestRepo1 {
    public static void insertProcess() throws Exception {

        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();


        RDV rdv = new RDV();
        Date dateRDV = new Date(104, 8, 9);
        Time heure = Time.valueOf("14:30:45");
        rdv.setIddossier(1);
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
        consultation.setIdDossier(1);
        consultation.setObservationMedecin("Besoin d'une intervention rapide");
        consultation.setId_medecin(1);
        consultation.setIdStatut(1);
        consultation.setId_rdv(idRdvGenere); // Lien correct

        consultationDAO.create(consultation);


        int idConsultGenere = consultationDAO.last_id();
        Consultation consultationInserted = consultationDAO.findById(idConsultGenere);
        System.out.println("Consultation insérée : " + consultationInserted);


        Certificat cert1 = Certificat.builder()
                .dateDebut(new Date())
                .dateFin(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .nature("Repos maladie")
                .noteMedecin("Patient à reposer")
                .idDossier(1)
                .idConsult(idConsultGenere)
                .build();

        certDAO.create(cert1);


        int idCertGenere = certDAO.get_last_id();
        Certificat certInserted = certDAO.findById(idCertGenere);
        System.out.println("Certificat inséré : " + certInserted);
    }


    public static void updateProcess(Certificat cert, RDV rdv, Consultation consultation) throws Exception {
        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        // Affichage avant update
        System.out.println("Avant update :");
        System.out.println("RDV : " + rdv);
        System.out.println("Consultation : " + consultation);
        System.out.println("Certificat : " + cert);


        rdvDAO.update(rdv);
        RDV rdvUpdated = rdvDAO.findById(rdv.getIdRDV());


        consultationDAO.update(consultation);
        Consultation consultationUpdated = consultationDAO.findById(consultation.getIdConsult());


        certDAO.update(cert);
        Certificat certUpdated = certDAO.findById(cert.getIdCert());

        // Affichage après update
        System.out.println("\nAprès update :");
        System.out.println("RDV : " + rdvUpdated);
        System.out.println("Consultation : " + consultationUpdated);
        System.out.println("Certificat : " + certUpdated);
    }

    public static void deleteProcessCascade(RDV rdv) throws Exception {
        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        // 1. Récupérer toutes les consultations liées au RDV
        List<Consultation> consultations = consultationDAO.findByRdv(rdv.getIdRDV());

        for (Consultation c : consultations) {
            // 2. Supprimer tous les certificats liés à cette consultation
            List<Certificat> certs = certDAO.findByConsultation(c.getIdConsult());
            for (Certificat cert : certs) {
                certDAO.delete(cert);
                System.out.println("Certificat supprimé : " + cert);
            }

            // 3. Supprimer la consultation
            consultationDAO.delete(c);
            System.out.println("Consultation supprimée : " + c);
        }

        // 4. Supprimer le RDV
        rdvDAO.deleteById(rdv.getIdRDV());
        System.out.println("RDV supprimé : " + rdv);
    }

    public static void main(String[] args) throws Exception {

        // --- 1. INSERTION ---
        System.out.println("=== INSERTION ===");
        insertProcess();

        // Récupération des derniers IDs pour update et delete
        RDVDAOImpl rdvDAO = new RDVDAOImpl();
        ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
        CertificatDaoimpl certDAO = new CertificatDaoimpl();

        int lastRdvId = rdvDAO.get_last_id();
        int lastConsultId = consultationDAO.last_id();
        int lastCertId = certDAO.get_last_id();

        RDV rdv = rdvDAO.findById(lastRdvId);
        Consultation consultation = consultationDAO.findById(lastConsultId);
        Certificat cert = certDAO.findById(lastCertId);

        // --- 2. UPDATE ---
        System.out.println("\n=== UPDATE ===");
        // Exemple de modification
        rdv.setMotif("Changement du motif");
        consultation.setObservationMedecin("Observation mise à jour");
        cert.setNoteMedecin("Note médecin mise à jour");

        updateProcess(cert, rdv, consultation);

        // --- 3. DELETE ---
        System.out.println("\n=== DELETE ===");
        deleteProcessCascade(rdv);

// Vérification que tout a été supprimé
        RDV rdvCheck = rdvDAO.findById(lastRdvId);
        Consultation consultCheck = consultationDAO.findById(lastConsultId);
        Certificat certCheck = certDAO.findById(lastCertId);

        System.out.println("\n=== VÉRIFICATION APRÈS DELETE ===");
        System.out.println("RDV existant ? " + rdvCheck);          // devrait afficher null
        System.out.println("Consultation existante ? " + consultCheck); // devrait afficher null
        System.out.println("Certificat existant ? " + certCheck);      // devrait afficher null

    }


}

