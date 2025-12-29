package ma.prodenta.mvc.controllers.modules.dossierMedical.api;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;

import java.util.List;

public interface DossierMedicalControlleur_Api {
    public List<Dossier_Medical_vu_generale_DTO> find_all_view() throws Exception;
    public List<Ordonnance>  find_all_ordonnance();
    public List<Consultation>  find_all_consultation();
    public void supprimer_dossier(int id ) throws Exception;
}
