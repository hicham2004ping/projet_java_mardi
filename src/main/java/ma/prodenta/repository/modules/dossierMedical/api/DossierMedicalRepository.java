package ma.prodenta.repository.modules.dossierMedical.api;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;
import ma.prodenta.repository.common.CrudRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DossierMedicalRepository extends CrudRepository<DossierMedical,Integer> {
    public DossierMedical find_patient(Patient patient) throws SQLException ;
    public List<Ordonnance> find_ordonances(DossierMedical dossier) throws SQLException;
    public int total_consultations(Patient patient) throws SQLException, IOException ;
    public int total_ordonances(Patient patient) throws SQLException, IOException;
    public int total_dossier_existe()  throws SQLException, IOException;
    public int total_rendez_vous(Patient patient) throws SQLException, IOException ;
    public boolean supprimer_dossier_medical_patient(Patient patient) throws SQLException;
    public int total_certificat_patient(Patient patient) throws SQLException, IOException ;
    public List<Dossier_Medical_vu_generale_DTO> find_all_view() throws SQLException;
}
