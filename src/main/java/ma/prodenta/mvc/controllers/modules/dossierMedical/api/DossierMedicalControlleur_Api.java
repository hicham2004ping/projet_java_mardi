package ma.prodenta.mvc.controllers.modules.dossierMedical.api;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;

import java.util.List;

public interface DossierMedicalControlleur_Api {
    public List<Dossier_Medical_vu_generale_DTO> find_all_view() throws Exception;
    public List<Ordonnance>  find_all_ordonnance();
    public List<Consultation>  find_all_consultation();
    public void supprimer_dossier(int id ) throws Exception;
    public DossierMedical find_by_id(int id) throws Exception;
    public Patient find_patient(int idDossier) throws Exception;
    public int nombre_consultations(Patient patient) throws Exception;
    public int total_dossier_mediceaux() throws Exception;
    public int total_ordonnance_patient(Patient patient) throws Exception;
}
