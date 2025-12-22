package ma.prodenta.service.test.dossier_medical;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.service.modules.dossierMedical.impl.DossierMedicalServiceImpl;
public class Dossier_Medical_test {

    public static void main(String[] args) {

        try {
            DossierMedicalServiceImpl dossierMedicalServiceImpl = Application_contexte.getdossierMedicalService();
            Patient patient = new Patient();
            patient.setId(73);
            dossierMedicalServiceImpl.find_dossier(patient);

            int totalConsultations =dossierMedicalServiceImpl.total_consultations_Patient(patient);
            System.out.println("Total consultations : " + totalConsultations);
            int totalOrdonnances = dossierMedicalServiceImpl.total_ordonances_Patient(patient);
            System.out.println("Total ordonnances : " + totalOrdonnances);

            int totalRdv = dossierMedicalServiceImpl.total_rendez_vous(patient);
            System.out.println("Total rendez-vous : " + totalRdv);

            int totalCertificats = dossierMedicalServiceImpl.total_certificat_patient(patient);
            System.out.println("Total certificats : " + totalCertificats);

            int totalDossiers = dossierMedicalServiceImpl.total_dossier_existe();
            System.out.println("Total dossiers médicaux : " + totalDossiers);

            System.out.println("\n TEST DOSSIER MEDICAL TERMINÉ AVEC SUCCÈS");

        } catch (Exception e) {
            System.err.println(" ERREUR LORS DU TEST DOSSIER MEDICAL");
            System.out.println(e.getMessage());
        }
    }
}
