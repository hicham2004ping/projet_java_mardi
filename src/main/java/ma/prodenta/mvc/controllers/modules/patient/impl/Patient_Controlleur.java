package ma.prodenta.mvc.controllers.modules.patient.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.mvc.dto.patient.PatientDTO;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.assurance.implement.Assurance_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.sexe.impl.Sexe_impl;
import ma.prodenta.service.modules.antecedent.impl.Antecedent_Service_ServiceImpl;
import ma.prodenta.service.modules.patient.api.PatientService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ma.prodenta.mvc.controllers.modules.antecedent.impl.Antecedent_Controlleur_Impl;

public class Patient_Controlleur {
    private PatientService patientService;
    private Sexe_impl sexe;
    private Assurance_impl  assurance;
    private Antecedent_impl antecedent;

    public Patient_Controlleur() {
        sexe=Application_contexte.getSexe_impl();
        assurance=Application_contexte.getAssurance_impl();
        antecedent=Application_contexte.getAntecedentRepository();
        patientService=Application_contexte.getpatientService();
    }

    public void creation_patient(int idMedecin, String nom, String prenom, LocalDate date_naissance , String adresse, String email, String telephone, String sexe, String assurance, List<Antecedent> antecedents) throws Exception {
        Patient p=new Patient();
        p.setAdresse(adresse);
        p.setEmail(email);
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setDateNaissance(date_naissance);
        p.setAntecedents(antecedents);
        p.setTelephone(telephone);
        try{
            Assurance assurance1=Assurance.get_Assurance_by_Libelle(assurance);
            Sexe sexe1=Sexe.get_sexeby_libelle(sexe);
            p.setSexe(sexe1);
            p.setAssurance(assurance1);
            patientService.creation(p,idMedecin);
        }
        catch(Exception e){
            throw new Exception("erreur lors de la creation du patient ");
        }
    }

    public List<PatientDTO> afficher_tous(){
            Patient_impl patientRepo = new Patient_impl();
            List<PatientDTO> patientDTOs = new ArrayList<PatientDTO>();
            PatientDTO patientDto=new PatientDTO();
            List<Patient> patients=new ArrayList<>();
            try{
                patients=patientRepo.findAll();
                for(Patient p:patients){
                    patientDto= PatientDTO.patientParseDto(p);
                    patientDTOs.add(patientDto);
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            return patientDTOs;
        }

    public PatientDTO afficher_patient(int id){
        Patient patient=new Patient();
        try{
            patient=patientService.find_by_id(id);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return PatientDTO.patientParseDto(patient);
    }
    public void supprimer_patient(int id) throws Exception {
        patientService.delete_by_id(id);
    }
}
