package ma.prodenta.mvc.controllers.modules.dossierMedical;

import ma.prodenta.mvc.dto.dossiermedical.DossierMedicalDto;
import ma.prodenta.service.common.DossierMedicalService;

import javax.swing.*;
import java.util.List;

/**
        relier Swing au service Dossie médical.
 */
public class DossierMedicalController {

    private final DossierMedicalService dossierService;

    public DossierMedicalController(DossierMedicalService dossierService) {
        this.dossierService = dossierService;
    }

    public List<DossierMedicalDto> loadDossiersForPatient(Long patientId, JFrame parent) {
        try {
            return dossierService.getDossiersByPatient(patientId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Erreur lors du chargement des dossiers : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }

    public void saveOrUpdateDossier(DossierMedicalDto dto, JFrame parent) {
        try {
            if (dto.getId() == null) {
                dossierService.createDossier(dto);
                JOptionPane.showMessageDialog(parent,
                        "Dossier médical créé avec succès.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                dossierService.updateDossier(dto);
                JOptionPane.showMessageDialog(parent,
                        "Dossier médical mis à jour avec succès.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Erreur lors de l'enregistrement du dossier : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteDossier(Long id, JFrame parent) {
        int confirm = JOptionPane.showConfirmDialog(parent,
                "Voulez-vous vraiment supprimer ce dossier ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dossierService.deleteDossier(id);
                JOptionPane.showMessageDialog(parent,
                        "Dossier médical supprimé.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "Erreur lors de la suppression : " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
