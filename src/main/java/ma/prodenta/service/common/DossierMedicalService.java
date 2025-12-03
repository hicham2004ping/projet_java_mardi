package ma.prodenta.service.common;

import ma.prodenta.mvc.dto.dossiermedical.DossierMedicalDto;

import java.util.List;

public interface DossierMedicalService {

    DossierMedicalDto getDossierById(Long id);

    List<DossierMedicalDto> getDossiersByPatient(Long patientId);

    DossierMedicalDto createDossier(DossierMedicalDto dto);

    DossierMedicalDto updateDossier(DossierMedicalDto dto);

    void deleteDossier(Long id);
}
