package ma.prodenta.service.modules.dossierMedical.api;

import ma.prodenta.entities.En.DossierMedical;
import java.util.List;

public interface DossierMedicalService {

    // Retourner la liste de tous les dossiers médicaux
    List<DossierMedical> findAll() throws Exception;

    // Trouver un dossier par son ID
    DossierMedical findById(Integer id) throws Exception;

    // Créer un dossier médical
    DossierMedical create(DossierMedical dossier) throws Exception;

    // Modifier un dossier médical
    DossierMedical update(DossierMedical dossier) throws Exception;

    // Supprimer un dossier par son ID
    boolean delete(Integer id) throws Exception;

    // (Optionnel) Récupérer tous les dossiers d’un patient
    List<DossierMedical> findByPatient(Integer idPatient) throws Exception;
}

