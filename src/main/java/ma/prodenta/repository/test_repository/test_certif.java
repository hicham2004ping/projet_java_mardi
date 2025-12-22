package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Certificat;
import ma.prodenta.repository.modules.certificat.impl.CertificatDaoimpl;

import java.util.Date;
import java.util.List;

public class test_certif {

    public static void test_certificat() {
        try {
            System.out.println("******** TEST COMPLET CERTIFICAT ********");

            CertificatDaoimpl certDAO = new CertificatDaoimpl();

            // --- Création ---
            System.out.println("\n--- Création Certificat ---");
            Certificat cert = Certificat.builder()
                    .dateDebut(new Date()) // date actuelle
                    .dateFin(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)) // +7 jours
                    .nature("Repos maladie")
                    .noteMedecin("Patient à reposer")
                    .idDossier(1)
                    .idConsult(1) // Associer à une consultation existante
                    .build();

            boolean created = certDAO.create(cert);
            if (!created) {
                System.out.println("Erreur lors de la création du certificat");
                return;
            }

            Integer lastId = cert.getIdCert();
            System.out.println("Certificat créé, ID = " + lastId);

            // --- Lecture ---
            System.out.println("\n--- Lecture Certificat ---");
            Certificat certLu = certDAO.findById(lastId);
            if (certLu != null) {
                System.out.println("Certificat lu : " + certLu.getNature() + " - " + certLu.getNoteMedecin()
                        + " | Consultation ID : " + certLu.getIdConsult());
            } else {
                System.out.println("Certificat introuvable");
            }

            // --- Modification ---
            System.out.println("\n--- Modification Certificat ---");
            certLu.setNoteMedecin("Repos prolongé");
            certLu.setNature("Repos maladie modifié");
            certDAO.update(certLu);

            // Lecture après modification
            Certificat certUpdated = certDAO.findById(lastId);
            System.out.println("Certificat modifié : " + certUpdated.getNature() + " - " + certUpdated.getNoteMedecin()
                    + " | Consultation ID : " + certUpdated.getIdConsult());

            // --- Lecture de tous les certificats ---
            System.out.println("\n--- Liste de tous les certificats ---");
            List<Certificat> allCerts = certDAO.findAll();
            for (Certificat c : allCerts) {
                System.out.println("[ID " + c.getIdCert() + "] Dossier " + c.getIdDossier()
                        + " | " + c.getDateDebut() + " → " + c.getDateFin()
                        + " | Nature : " + c.getNature()
                        + " | Note : " + c.getNoteMedecin()
                        + " | Consultation ID : " + c.getIdConsult());
            }

            // --- Suppression ---
            System.out.println("\n--- Suppression Certificat ---");
            boolean deleted = certDAO.deleteById(lastId);
            System.out.println("Suppression : " + (deleted ? "OK" : "ECHEC"));

        } catch (Exception e) {
            System.out.println("Erreur dans le test Certificat : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test_certificat();
    }
}
