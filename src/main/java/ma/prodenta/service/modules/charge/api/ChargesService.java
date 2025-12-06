package ma.prodenta.service.modules.charge.api;

import ma.prodenta.entities.En.Charges;

import java.util.List;

public interface ChargesService {

    /**
     * Récupérer une charge par son ID
     */
    Charges getChargeById(Long id) throws Exception;

    /**
     * Créer une nouvelle charge
     * @return true si l’insertion a réussi
     */
    boolean createCharge(Charges charge) throws Exception;

    /**
     * Récupérer la liste de toutes les charges
     */
    List<Charges> getAllCharges() throws Exception;

    /**
     * Mettre à jour une charge existante
     */
    void updateCharge(Charges charge) throws Exception;

    /**
     * Supprimer une charge par ID
     * @return true si la suppression a réussi
     */
    boolean deleteChargeById(Long id) throws Exception;
}
