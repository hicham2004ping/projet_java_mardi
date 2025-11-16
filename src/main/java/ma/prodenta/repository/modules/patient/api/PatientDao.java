package ma.prodenta.repository.modules.patient.api;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.common.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface PatientDao  extends ma.prodenta.repository.common.CrudRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByTelephone(String telephone);
    List<Patient> searchByNomPrenom(String keyword); // LIKE %keyword%
    boolean existsById(Long id);
    long count();
    List<Patient> findPage(int limit, int offset);

    // ---- Liaison Many-to-Many ----
    void addAntecedentToPatient(Long patientId, Long antecedentId);
    void removeAntecedentFromPatient(Long patientId, Long antecedentId);
    void removeAllAntecedentsFromPatient(Long patientId);
    List<Antecedent> getAntecedentsOfPatient(Long patientId);
    List<Patient> getPatientsByAntecedent(Long antecedentId);

}
