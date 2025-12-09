package ma.prodenta.repository.modules.consultation.api;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultationDao extends CrudRepository<Consultation,Integer> {

    // Récupérer une consultation par son ID

    // Récupérer les consultations d'un dossier
    List<Consultation> findByDossier(Integer idDossier) throws Exception;
    public int get_last_id();
    public List<Consultation> findByRdv(Integer idRdv) throws Exception;

    // Ajouter une nouvelle consultation
}
