package ma.prodenta.service.modules.situationfinanciere.api;

import ma.prodenta.entities.En.SituationFinanciere;

import java.util.List;

public interface SituationFinanciereService {

    // Récupérer toutes les situations financières
    List<SituationFinanciere> findAll() throws Exception;

    // Récupérer une situation financière par son id
    SituationFinanciere findById(Integer idSF) throws Exception;

    // Créer une nouvelle situation financière
    boolean create(SituationFinanciere sf) throws Exception;

    // Mettre à jour une situation existante
    void update(SituationFinanciere sf) throws Exception;

    // Supprimer via l'objet
    boolean delete(SituationFinanciere sf) throws Exception;

    // Supprimer via l'id
    boolean deleteById(Integer idSF) throws Exception;

    // (optionnel) Récupérer les situations par patient
    List<SituationFinanciere> findByPatient(Integer idPatient) throws Exception;
}
