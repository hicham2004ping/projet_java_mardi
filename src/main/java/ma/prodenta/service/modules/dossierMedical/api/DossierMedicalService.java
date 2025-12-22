package ma.prodenta.service.modules.dossierMedical.api;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
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
}

