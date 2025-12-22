package ma.prodenta.service.modules.dossierMedical.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.dossiermedical.DossierMedicalDto;
import ma.prodenta.repository.common.DossierMedicalRepository;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.service.modules.dossierMedical.api.DossierMedicalService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new Exception("le patient n'existe pas ");
        }
        if (patient.getId()<=0){
            throw new Exception("l'id du patient est invalide ");
        }
        return dossier_medical_impl.total_consultations(patient);
    }

    @Override
    public int total_ordonances_Patient(Patient patient) throws Exception {
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
        if(dossier_medical_impl.total_ordonances(patient)==0){
            throw new Exception("le patient n'a pas encore effecteur une consultation");
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
}