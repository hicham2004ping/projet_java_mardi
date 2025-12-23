package ma.prodenta.service.test.patient;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.service.modules.patient.api.PatientService;
import ma.prodenta.service.modules.patient.baseImplementation.PatientServiceImpl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient_Service_Tesst {

    public static void main(String[] args) {
        Antecedent_impl antecedent_impll = Application_contexte.getAntecedentRepository();
        PatientService patientService = new PatientServiceImpl();
        List<Antecedent> antecedents1 = new ArrayList<>();
        try {
            Patient p = new Patient();
            p.setNom("karaki");
            p.setPrenom("ali");
            p.setEmail("ali@email.com");
            p.setTelephone("0611111111");
            p.setDateNaissance(LocalDate.of(2024, 9, 27));
            p.setAdresse("Bierut");
            p.setAssurance(Assurance.CNSS);
            int idMedecin = 1;

            for(int i=1;i<=3;i++){
                antecedents1.add(antecedent_impll.findById(i));
            }

            p.setAntecedents(antecedents1);
            patientService.creation(p, idMedecin);
            System.out.println("ID patient créé : " + p.getId());

            int patientId = p.getId();

            Patient pByEmail = patientService.find_by_email("wissam@email.com");
            System.out.println("Patient trouvé par email : " + pByEmail.getNom() + " " + pByEmail.getPrenom());

            List<Patient> patientsByName = patientService.search_by_nom_prenom("ha");

            System.out.println("Patients trouvés par mot clé : " + patientsByName.size());


            long count = patientService.count();
            System.out.println("Nombre total de patients : " + count);

           List<Patient> allPatients = patientService.find_all();
            System.out.println("Liste complète des patients : " + allPatients.size());


            List<Antecedent> antecedents = patientService.get_antecedents_of_patient(patientId);

           System.out.println("Antécédents du patient : " + antecedents.size());

           patientService.supprimer_antecedent_from_patient(patientId, 1);
            System.out.println("Antécédent supprimé");

            patientService.remove_all_antecedents_from_patient(patientId);
            System.out.println("Tous les antécédents supprimés");

            System.out.println("\n TEST MAIN TERMINÉ AVEC SUCCÈS");

        } catch (Exception e) {
            System.err.println(" ERREUR LORS DU TEST MAIN");
            System.out.println(e.getMessage());
        }
    }
}
