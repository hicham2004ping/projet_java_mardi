package ma.prodenta.service.modules.caisse.api;

import ma.prodenta.entities.En.Charges;

import java.util.List;

public interface ChargesService {

    // Récupérer toutes les charges
    List<Charges> findAll() throws Exception;

    // Récupérer une charge par son id
    Charges findById(Integer id) throws Exception;

    // Créer une nouvelle charge
    Charges create(Charges charge) throws Exception;

    // Mettre à jour une charge existante
    Charges update(Charges charge) throws Exception;

    // Supprimer une charge par son id
    boolean delete(Integer id) throws Exception;

    // (optionnel) Récupérer les charges d'un cabinet
    List<Charges> findByCabinet(Integer idCabinet) throws Exception;
}

