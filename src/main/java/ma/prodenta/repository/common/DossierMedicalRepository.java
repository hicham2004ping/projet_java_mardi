package ma.prodenta.repository.common;

import ma.prodenta.entities.En.DossierMedical;

import java.util.List;
import java.util.Optional;

/**
 accès aux dossiers médicaux.
 */
public interface DossierMedicalRepository {

    Optional<DossierMedical> findById(Long idDossier);

    List<DossierMedical> findByPatientId(Long idPatient);

    DossierMedical save(DossierMedical idDossier);

    DossierMedical update(DossierMedical idDossier);

    void delete(Long idDossier);
}
