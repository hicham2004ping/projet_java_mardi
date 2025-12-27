package ma.prodenta.service.modules.cabinetMedical.api;

import ma.prodenta.entities.En.CabinetMedical;
import ma.prodenta.service.common.Service;

import java.io.Serial;
import java.util.List;

public interface cabinetMedicalService extends Service<CabinetMedical,Integer> {

    List<CabinetMedical> findAll() throws Exception;                 // récupérer tous les cabinets
    List<CabinetMedical> findByNom(String nom) throws Exception;     // recherche par nom
    List<CabinetMedical> findByVille(String ville) throws Exception; // recherche par adresse / ville
    boolean existByEmail(String email) throws Exception;             // vérifie si l'email existe
    CabinetMedical findLastCreated() throws Exception;
}
