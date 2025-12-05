package ma.prodenta.repository.modules.consultation.api;

import ma.prodenta.entities.En.Consultation;

import java.util.List;
import java.util.Optional;

public interface ConsultationDao {

    // Récupérer toutes les consultations
    List<Consultation> findAll() throws Exception;

    // Récupérer une consultation par son ID
    Optional<Consultation> findById(Integer idConsult) throws Exception;

    // Récupérer les consultations d'un dossier
    List<Consultation> findByDossier(Integer idDossier) throws Exception;

    // Ajouter une nouvelle consultation
    Consultation save(Consultation consultation) throws Exception;

    // Mettre à jour une consultation
    void update(Consultation consultation) throws Exception;

    // Supprimer une consultation
    void delete(Integer idConsult) throws Exception;
}
