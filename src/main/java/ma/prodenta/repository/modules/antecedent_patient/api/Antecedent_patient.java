package ma.prodenta.repository.modules.antecedent_patient.api;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.common.CrudRepository;
import java.sql.SQLException;
import java.util.List;

public interface Antecedent_patient extends CrudRepository<Patient,Integer> { public List<Antecedent> find_antecedent_by_patient(Patient patient) throws SQLException;
public List<Patient> find_patients_by_antecedent(Antecedent antecedent) throws SQLException;
    public int nombre_antecedent_par_patient(Patient patient) throws SQLException;
    public boolean supprimer_antecedent_par_patient(Patient patient) throws SQLException;
    public int get_last_id();
    public boolean ajouter_antecedent_patient(Patient p,Antecedent antecedent) throws SQLException;
    public boolean supprimer_antecedent_patient(Patient p,Antecedent antecedent) throws SQLException;
}
