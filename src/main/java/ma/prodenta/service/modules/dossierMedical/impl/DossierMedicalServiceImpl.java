package ma.prodenta.service.modules.dossierMedical.impl;
import ma.prodenta.common.exceptions.ArgumentException;
import ma.prodenta.common.exceptions.ErreurLectureException;
import ma.prodenta.common.exceptions.ErreurSuppressionException;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.service.modules.dossierMedical.api.DossierMedicalService;

import java.util.List;

public class DossierMedicalServiceImpl implements DossierMedicalService {
    @Override
    public void find_dossier(Patient p) throws Exception {
        Dossier_medical_impl dossier_medical_impl= Application_contexte.getDossierMedicalRepository();
        if(p==null){
            throw new Exception("le patient que vous avez entrez est null");
        }
        if(p.getId()<=0){
            throw new Exception("l'id que vous avez entrez est invalide");
        }
        DossierMedical dossier=dossier_medical_impl.find_patient(p);
        if(dossier==null){
            throw new Exception("le patient que vous avez entrez est invalide");
        }
        System.out.println("l'id du dossier medical qui s'appartient au patient numer"+p.getId()+" est "+dossier.getIdDossier());
    }

    @Override
    public int total_consultations_Patient(Patient patient) throws Exception {
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
        if(patient==null){
            throw new ErreurLectureException("le patient n'existe pas ");
        }
        if (patient.getId()<=0){
            throw new ArgumentException("l'id du patient est invalide ");
        }
        return dossier_medical_impl.total_consultations(patient);
    }

    @Override
    public int total_ordonances_Patient(Patient patient) throws Exception {
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
        if(patient==null||patient.getId()<=0){
            throw new ErreurLectureException("ce patient est invalide ");
        }
        return dossier_medical_impl.total_ordonances(patient);
    }

    @Override
    public int total_dossier_existe() throws Exception {
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
        if(dossier_medical_impl.total_dossier_existe()==0){
            throw new Exception("il y'a aucun dossier medicales dans la base ");
        }
      return  dossier_medical_impl.total_dossier_existe();
    }

    @Override
    public int total_rendez_vous(Patient patient) throws Exception {
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
        if (dossier_medical_impl.total_rendez_vous(patient)==0){
            throw new Exception("ce patient n'a pas encore effectuer aucun rendez-vous");
        }
        return dossier_medical_impl.total_rendez_vous(patient);
    }

    @Override
    public void supprimer_dossier_patient(Patient patient) throws Exception {
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
        DossierMedical dossier=new DossierMedical();
        if(patient==null){
            throw new Exception("merci de saisir un patient valide");
        }
        dossier=dossier_medical_impl.find_patient(patient);
        if(dossier==null){
            throw new Exception("le patient que vous avez entrez n'existe pas dans la base");
        }
        if(!dossier_medical_impl.supprimer_dossier_medical_patient(patient)){
            throw new Exception("erreur lors de la suppresion du dossier");
        }
        System.out.println("suppression du dossier medical avec success");
    }

    @Override
    public int total_certificat_patient(Patient patient) throws Exception {
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
        if(patient==null){
            throw new Exception("le patient n'existe pas ");
        }
        return dossier_medical_impl.total_certificat_patient(patient);
    }

    @Override
    public List<Dossier_Medical_vu_generale_DTO> find_all_view() throws Exception {
        Dossier_medical_impl dossier=Application_contexte.getDossierMedicalRepository();
        return dossier.find_all_view();
    }

    @Override
    public void delte_by_id(int id) throws Exception {
        Dossier_medical_impl dossier_repo=Application_contexte.getDossierMedicalRepository();
        if(id<=0){
            throw new ArgumentException("l'argument passer a la fonction de recherche est non valide ");
        }
        DossierMedical dossierMedical= dossier_repo.findById(id);

        if(dossierMedical==null){
            throw new ErreurLectureException("aucun dossier n'existe avec cette id ");
        }
        if(!dossier_repo.deleteById(id)){
            throw new ErreurSuppressionException("impossible de supprimer ce dossier");
        }
        System.out.println("suppression avec success du dossier");
    }

    @Override
    public DossierMedical find_by_id(int id) throws Exception {
        if(id<=0){
            throw new ArgumentException("l'id du dossier est invalide");
        }
        Dossier_medical_impl dossierRepo=Application_contexte.getDossierMedicalRepository();
        DossierMedical dossier=new DossierMedical();
        try{
            dossier=dossierRepo.findById(id);
            if(dossier==null){
                throw new  ErreurLectureException("impossible de retrouner un dossier");
            }
            return dossier;
        }
        catch(ArgumentException | ErreurLectureException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Patient find_patient(int id) throws Exception {
        if(id<=0){
            throw new ArgumentException("l'id du dossier est invalide");
        }
        Patient patient=new Patient();
        Patient_impl patientRepo=Application_contexte.getPatientRepository();
        Dossier_medical_impl dossierRepo=Application_contexte.getDossierMedicalRepository();
        DossierMedical dossier=new DossierMedical();

        dossier=dossierRepo.findById(id);
        if(dossier==null){
            throw new ErreurLectureException("impossible de lire le dossier medical ");
        }
        patient=patientRepo.findById(dossier.getIdPatient());
        if(patient==null){
            throw new ErreurLectureException("impossible de lire le patient dont le dossier medical appartient");
        }
        return patient;
    }
}