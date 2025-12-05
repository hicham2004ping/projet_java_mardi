
package ma.prodenta.repository.modules.dossierMedical.api;

import ma.prodenta.entities.En.DossierMedical;

import java.util.List;

public interface DossierMedicalRepository {
    DossierMedical findById(Integer id) throws Exception;
    List<DossierMedical> findAll() throws Exception;
    DossierMedical save(DossierMedical dossier) throws Exception;   // insert
    DossierMedical update(DossierMedical dossier) throws Exception; // update
    void delete(Integer id) throws Exception;
}
