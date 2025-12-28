package ma.prodenta.service.common;

import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;

import java.util.List;

public interface DossierMedicalService {

    Dossier_Medical_vu_generale_DTO getDossierById(Long id);

    List<Dossier_Medical_vu_generale_DTO> getDossiersByPatient(Long patientId);

    Dossier_Medical_vu_generale_DTO createDossier(Dossier_Medical_vu_generale_DTO dto);

    Dossier_Medical_vu_generale_DTO updateDossier(Dossier_Medical_vu_generale_DTO dto);

    void deleteDossier(Long id);
}
