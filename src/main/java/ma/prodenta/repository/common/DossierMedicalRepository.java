package ma.prodenta.repository.common;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.DossierMedical;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 accès aux dossiers médicaux.
 */
public interface DossierMedicalRepository {

    List<DossierMedical> findAll() throws Exception;

    DossierMedical find_patient(Integer id) throws SQLException;

    DossierMedical findById(Integer integer) throws Exception;

    boolean create(DossierMedical objet) throws SQLException, IOException;

    boolean deleteById(Integer integer) throws SQLException, Exception;

    Optional<Antecedent> findByNom(String nom);

    Optional<DossierMedical> findById(Long idDossier);

    List<DossierMedical> findByPatientId(Long idPatient);

    DossierMedical save(DossierMedical idDossier);

    DossierMedical update(DossierMedical idDossier);

    void delete(Long idDossier);
}
