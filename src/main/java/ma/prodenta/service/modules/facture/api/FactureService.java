package ma.prodenta.service.modules.facture.api;

import ma.prodenta.entities.En.Facture;

import java.util.List;

public interface FactureService {

    // Récupérer toutes les factures
    List<Facture> findAll() throws Exception;

    // Récupérer une facture par id
    Facture findById(Integer idFact) throws Exception;

    // Créer une nouvelle facture
    boolean create(Facture facture) throws Exception;

    // Mettre à jour une facture existante
    void update(Facture facture) throws Exception;

    // Supprimer une facture via l'objet
    boolean delete(Facture facture) throws Exception;

    // Supprimer une facture via son id
    boolean deleteById(Integer idFact) throws Exception;

    // (optionnel) récupérer les factures par situation financière
    List<Facture> findBySituationFinanciere(Integer idSF) throws Exception;
}
