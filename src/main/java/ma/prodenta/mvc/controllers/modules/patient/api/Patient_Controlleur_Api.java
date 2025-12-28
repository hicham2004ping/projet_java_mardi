package ma.prodenta.mvc.controllers.modules.patient.api;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import java.time.LocalDate;
import java.util.List;

public interface Patient_Controlleur_Api {
    public void creation_patient(int idMedecin, String nom, String prenom, LocalDate date_naissance , String adresse, String email, String telephone, String sexe, String assurance, List<Antecedent> antecedents) ;
    public List<Patient> afficher_tous();
    public Patient afficher_patient(int id);
}
