package ma.prodenta.repository.modules.dossierMedical.api;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.common.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface DossierMedicalRepository extends CrudRepository<DossierMedical,Integer> {
    public DossierMedical find_patient(Patient patient) throws SQLException ;
}
