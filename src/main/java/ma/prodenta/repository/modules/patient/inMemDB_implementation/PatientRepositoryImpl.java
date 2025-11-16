package ma.prodenta.repository.modules.patient.inMemDB_implementation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.modules.patient.api.PatientDao;

public class PatientRepositoryImpl implements PatientDao {
    @Override
    public Optional<Patient> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Patient> findByTelephone(String telephone) {
        return Optional.empty();
    }

    @Override
    public List<Patient> searchByNomPrenom(String keyword) {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Patient> findPage(int limit, int offset) {
        return List.of();
    }

    @Override
    public void addAntecedentToPatient(Long patientId, Long antecedentId) {

    }

    @Override
    public void removeAntecedentFromPatient(Long patientId, Long antecedentId) {

    }

    @Override
    public void removeAllAntecedentsFromPatient(Long patientId) {

    }

    @Override
    public List<Antecedent> getAntecedentsOfPatient(Long patientId) {
        return List.of();
    }

    @Override
    public List<Patient> getPatientsByAntecedent(Long antecedentId) {
        return List.of();
    }

    @Override
    public List<Patient> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Patient findById(Long aLong) throws Exception {
        return null;
    }

    @Override
    public void create(Patient patient) {

    }

    @Override
    public void update(Patient patient) {

    }

    @Override
    public void delete(Patient patient) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}

