package ma.prodenta.service.modules.patient.api;
import ma.prodenta.common.exceptions.*;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient find_by_id(int id);
    void creation(Patient p,int IdMedecin) throws EmailExisteException, EmailInvalideException, ArgumentException, Date_Naissance_Exception, ErreurCreationException, SQLException, IOException;
    Patient find_by_email(String email) throws Exception;
    List<Patient> search_by_nom_prenom(String keyword) throws Exception;
    long count();
    List<Patient> find_all() throws Exception;
    void ajouter_antecedent_to_patient(int patientId, int antecedentId) throws Exception;
    void supprimer_antecedent_from_patient(int patientId, int antecedentId) throws Exception;
    void remove_all_antecedents_from_patient(int patientId) throws Exception;
    List<Antecedent> get_antecedents_of_patient(int patientId) throws Exception;
    List<Patient> get_patients_by_antecedent(int antecedentId) throws Exception;
    int get_last_id() throws SQLException, IOException;
    void delete_by_id(int id) throws Exception;
    public void update(Patient objet) throws SQLException, IOException, Exception ;
    }
