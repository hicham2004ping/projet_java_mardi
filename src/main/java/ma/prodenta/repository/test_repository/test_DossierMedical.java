package ma.prodenta.repository.test_repository;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;

import java.util.List;

public class test_DossierMedical {

    public static void main(String[] args) {

        Dossier_medical_impl dao = new Dossier_medical_impl();
        Patient_impl patientDao = new Patient_impl();

        try {
            // ---------------------- find patient ----------------------
            Patient patient = patientDao.findById(1); // Choisis un ID existant dans ta DB
            if (patient == null) {
                System.out.println("Patient introuvable !");
            } else {
                DossierMedical ds = dao.find_patient(patient);
                if (ds != null) {
                    System.out.println("Dossier trouvé pour patient ID " + ds.getIdPatient() +
                            ", Médecin ID " + ds.getIdMedecin());
                } else {
                    System.out.println("Aucun dossier trouvé pour ce patient.");
                }
            }

            // ---------------------- find all ----------------------
            List<DossierMedical> allDossiers = dao.findAll();
            System.out.println("\nTous les dossiers :");
            for (DossierMedical d : allDossiers) {
                System.out.println("Dossier ID: " + d.getIdDossier() +
                        ", Patient ID: " + d.getIdPatient() +
                        ", Médecin ID: " + d.getIdMedecin());
            }

            // ---------------------- find by ID ----------------------
            int rechercheId = 7; // Choisis un ID existant
            DossierMedical dsById = dao.findById(rechercheId);
            if (dsById != null) {
                System.out.println("\nDossier trouvé par ID " + rechercheId +
                        ": Patient ID " + dsById.getIdPatient() +
                        ", Médecin ID " + dsById.getIdMedecin());
            } else {
                System.out.println("\nAucun dossier trouvé avec l'ID " + rechercheId);
            }

            // ---------------------- create ----------------------
            DossierMedical newDossier = DossierMedical.builder()
                    .idPatient(5)
                    .idMedecin(2)
                    .build();
            boolean created = dao.create(newDossier);
            System.out.println("\nCréation du dossier : " + (created ? "Succès" : "Échec"));

            // ---------------------- delete by ID ----------------------
            int deleteId = 10;
            boolean deletedById = dao.deleteById(deleteId);
            System.out.println("\nSuppression du dossier ID " + deleteId + ": " +
                    (deletedById ? "Succès" : "Échec"));

            // ---------------------- delete by object ----------------------
            DossierMedical dossierToDelete = DossierMedical.builder()
                    .idDossier(15)
                    .build();
            boolean deletedObj = dao.delete(dossierToDelete);
            System.out.println("\nSuppression du dossier objet ID 15: " +
                    (deletedObj ? "Succès" : "Échec"));

            // ---------------------- get last ID ----------------------
            int lastId = dao.get_last_id();
            System.out.println("\nDernier ID de dossier : " + lastId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
