package ma.prodenta.mvc.controllers.modules.patient;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.assurance.implement.Assurance_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.sexe.impl.Sexe_impl;
import ma.prodenta.service.modules.patient.api.PatientService;
import java.time.LocalDate;
import java.util.List;

public class Creation_Patient_Controlleur {
    private PatientService patientService;
    private Sexe_impl sexe;
    private Assurance_impl  assurance;
    private Antecedent_impl antecedent;

    public Creation_Patient_Controlleur() {
        sexe=Application_contexte.getSexe_impl();
        assurance=Application_contexte.getAssurance_impl();
        antecedent=Application_contexte.getAntecedentRepository();
        patientService=Application_contexte.getpatientService();
    }

    public void creation_patient(int idMedecin, String nom, String prenom, LocalDate date_naissance , String adresse, String email, String telephone, String sexe, String assurance, List<Antecedent> antecedents) {
        Patient p=new Patient();
        p.setAdresse(adresse);
        p.setEmail(email);
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setDateNaissance(date_naissance);
        p.setAntecedents(antecedents);
        try{
            Sexe sexe1=Sexe.valueOf(sexe);
            Assurance assurance1=Assurance.valueOf(assurance);
            p.setSexe(sexe1);
            p.setAssurance(assurance1);
            patientService.creation(p,idMedecin);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
