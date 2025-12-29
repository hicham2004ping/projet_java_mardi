package ma.prodenta.mvc.controllers.modules.dossierMedical;
import ma.prodenta.common.exceptions.ArgumentException;
import ma.prodenta.common.exceptions.ErreurCreationException;
import ma.prodenta.common.exceptions.ErreurLectureException;
import ma.prodenta.common.exceptions.ErreurSuppressionException;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;
import ma.prodenta.service.modules.dossierMedical.impl.DossierMedicalServiceImpl;
import ma.prodenta.mvc.controllers.modules.dossierMedical.api.DossierMedicalControlleur_Api;
import java.util.List;

public class DossierMedicalController implements DossierMedicalControlleur_Api {

    DossierMedicalServiceImpl dossierMedicalService;
    public DossierMedicalController(){
        try{
            dossierMedicalService =Application_contexte.getDossierMedicalServiceImpl();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Dossier_Medical_vu_generale_DTO> find_all_view() throws Exception {
       return  dossierMedicalService.find_all_view();
    }

    @Override
    public List<Ordonnance> find_all_ordonnance() {
        return List.of();
    }

    @Override
    public List<Consultation> find_all_consultation() {
        return List.of();
    }

    @Override
    public void supprimer_dossier(int id) throws Exception {
        try{
            this.dossierMedicalService.delte_by_id(id);
        }
        catch(ErreurSuppressionException | ErreurLectureException | ArgumentException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public DossierMedical find_by_id(int id) throws Exception {
        DossierMedicalServiceImpl service=Application_contexte.getDossierMedicalServiceImpl();
        try{
            return service.find_by_id(id);
        }
        catch(ArgumentException | ErreurLectureException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Patient find_patient(int idDossier) throws Exception {
        DossierMedicalServiceImpl service=Application_contexte.getDossierMedicalServiceImpl();
        try{
            return service.find_patient(idDossier);
        }
        catch (ArgumentException | ErreurLectureException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public int nombre_consultations(Patient patient) throws Exception {
        DossierMedicalServiceImpl service=Application_contexte.getDossierMedicalServiceImpl();
        try{
           return service.total_consultations_Patient(patient);
        }
        catch (ArgumentException | ErreurLectureException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public int total_dossier_mediceaux() throws Exception {
        DossierMedicalServiceImpl service=Application_contexte.getDossierMedicalServiceImpl();
        try{
            return service.total_dossier_existe();
        }
        catch (ErreurLectureException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public int total_ordonnance_patient(Patient patient) throws Exception {
        DossierMedicalServiceImpl service=Application_contexte.getDossierMedicalServiceImpl();
        try{
            return service.total_ordonances_Patient(patient);
        }
        catch (ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}