package ma.prodenta.service.modules.patient.api;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    boolean creation(Patient p) throws Exception;
    boolean creation_dossier_medical(Patient p,int id_medecin) throws SQLException, IOException;
    void update_patient(Patient p) throws SQLException;
    boolean delete_patient(Patient p) throws SQLException, IOException;
    boolean delete_patient_by_id(Integer id) throws SQLException, IOException;
    Patient find_by_id(Integer id) throws Exception;
    Patient find_by_email(String email);
    Optional<Patient> find_by_telephone(String tel);
    List<Patient> search_by_nom_prenom(String keyword);
    boolean exists_by_id(long id);
    long count();
    List<Patient> find_all() throws Exception;
    List<Patient> find_page(int limit, int offset);
    boolean add_antecedent_to_patient(int patientId, int antecedentId) throws Exception;
    boolean remove_antecedent_from_patient(int patientId, int antecedentId) throws Exception;
    boolean remove_all_antecedents_from_patient(int patientId) throws Exception;
    List<Antecedent> get_antecedents_of_patient(int patientId) throws Exception;
    List<Patient> get_patients_by_antecedent(int antecedentId) throws Exception;
    int get_last_id() throws SQLException, IOException;
    Optional<Antecedent> find_by_nom(String nom);
}
