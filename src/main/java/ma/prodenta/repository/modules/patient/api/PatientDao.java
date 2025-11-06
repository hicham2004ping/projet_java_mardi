package ma.prodenta.repository.modules.patient.api;

import ma.prodenta.entities.En.Patient;
import java.util.List;

public interface PatientDao {
    void create(Patient patient) throws Exception;
    void update(Patient patient) throws Exception;
    void delete(int idPatient) throws Exception;
    Patient FindById(int idPatient) throws Exception;
    List<Patient> FindAll() throws Exception;
}
