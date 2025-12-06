package ma.prodenta.service.modules.Revenus.api;

import ma.prodenta.entities.En.Revenus;

import java.util.List;

public interface RevenusService {

    /**
     * Récupère un revenu par son ID.
     */
    Revenus getRevenuById(Long id) throws Exception;

    /**
     * Crée un nouveau revenu.
     * @return true si l'opération réussit.
     */
    boolean createRevenu(Revenus revenu) throws Exception;

    /**
     * Récupère la liste complète des revenus.
     */
    List<Revenus> getAllRevenus() throws Exception;

    /**
     * Met à jour un revenu.
     */
    void updateRevenu(Revenus revenu) throws Exception;

    /**
     * Supprime un revenu par ID.
     * @return true si la suppression réussit.
     */
    boolean deleteRevenuById(Long id) throws Exception;
}
