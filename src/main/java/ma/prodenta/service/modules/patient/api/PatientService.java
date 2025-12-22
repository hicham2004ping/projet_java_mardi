package ma.prodenta.service.modules.patient.api;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    void creation(Patient p,int IdMedecin) throws Exception;
    void find_by_email(String email) throws Exception;
    void search_by_nom_prenom(String keyword) throws Exception;
    void count();
    void find_all() throws Exception;
    void ajouter_antecedent_to_patient(int patientId, int antecedentId) throws Exception;
    void supprimer_antecedent_from_patient(int patientId, int antecedentId) throws Exception;
    void remove_all_antecedents_from_patient(int patientId) throws Exception;
    void get_antecedents_of_patient(int patientId) throws Exception;
    void get_patients_by_antecedent(int antecedentId) throws Exception;
    void get_last_id() throws SQLException, IOException;
}
