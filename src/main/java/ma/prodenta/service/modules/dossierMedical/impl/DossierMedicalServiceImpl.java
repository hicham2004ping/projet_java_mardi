package ma.prodenta.service.modules.dossierMedical.impl;

import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.mvc.dto.dossiermedical.DossierMedicalDto;
import ma.prodenta.repository.common.DossierMedicalRepository;
import ma.prodenta.service.common.DossierMedicalService;

import java.util.List;
import java.util.stream.Collectors;

/**
 logique métier pour les dossiers médicaux.
 */
public class DossierMedicalServiceImpl implements DossierMedicalService {

    private final DossierMedicalRepository dossierRepository;

    public DossierMedicalServiceImpl(DossierMedicalRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    @Override
    public DossierMedicalDto getDossierById(Long id) {
        DossierMedical dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable pour l'id : " + id));
        return toDto(dossier);
    }

    @Override
    public List<DossierMedicalDto> getDossiersByPatient(Long patientId) {
        return dossierRepository.findByPatientId(patientId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DossierMedicalDto createDossier(DossierMedicalDto dto) {
        DossierMedical entity = toEntity(dto);
        DossierMedical saved = dossierRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public DossierMedicalDto updateDossier(DossierMedicalDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("L'id du dossier est obligatoire pour la mise à jour.");
        }
        DossierMedical entity = toEntity(dto);
        DossierMedical updated = dossierRepository.update(entity);
        return toDto(updated);
    }

    @Override
    public void deleteDossier(Long id) {
        dossierRepository.delete(id);
    }

    private DossierMedicalDto toDto(DossierMedical entity) {
        DossierMedicalDto dto = new DossierMedicalDto();
        dto.setId(entity.getId());
        dto.setPatientId(entity.getPatientId());
        dto.setAllergies(entity.getAllergies());
        dto.setAntecedents(entity.getAntecedents());
        dto.setNotes(entity.getNotes());
        return dto;
    }

    private DossierMedical toEntity(DossierMedicalDto dto) {
        DossierMedical entity = new DossierMedical();
        entity.setId(dto.getId());
        entity.setPatientId(dto.getPatientId());
        entity.setAllergies(dto.getAllergies());
        entity.setAntecedents(dto.getAntecedents());
        entity.setNotes(dto.getNotes());
        return entity;
    }
}
