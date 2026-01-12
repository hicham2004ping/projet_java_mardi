package ma.prodenta.service.modules.actes.api;

import ma.prodenta.entities.En.Acte;
import java.util.List;

public interface Acte_Service_api {
    List<Acte> getActesParCategorie(String categorie) throws Exception;

    List<Acte> rechercherActesParMotCle(String motCle) throws Exception;

    double calculerPrixMoyenActes() throws Exception;

    boolean existeActe(String libelle) throws Exception;

    Acte getActeLePlusCher() throws Exception;

    Acte getActeLeMoinsCher() throws Exception;

    List<Acte> trierActesParPrix(boolean ascendant) throws Exception;

    List<Acte> getAllActes() throws Exception;

    boolean ajouterActe(Acte acte) throws Exception;

    void modifierActe(Acte acte) throws Exception;

    boolean supprimerActe(Acte acte) throws Exception;

    boolean supprimerActeParId(int id) throws Exception;
}
