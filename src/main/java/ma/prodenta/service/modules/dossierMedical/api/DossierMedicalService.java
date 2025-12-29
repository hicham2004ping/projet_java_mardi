package ma.prodenta.service.modules.dossierMedical.api;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DossierMedicalService {
    public void find_dossier(Patient p) throws Exception;
    public int total_consultations_Patient(Patient patient) throws Exception;
    public int total_ordonances_Patient(Patient patient) throws Exception;
    public int total_dossier_existe() throws Exception;
    public int total_rendez_vous(Patient patient) throws Exception;
    public void supprimer_dossier_patient(Patient patient) throws Exception;
    public int total_certificat_patient(Patient patient) throws Exception;
    public List<Dossier_Medical_vu_generale_DTO> find_all_view() throws Exception;
    public void delte_by_id(int id) throws Exception;
    public DossierMedical find_by_id(int id) throws Exception;
    public Patient find_patient(int id) throws Exception;
}

