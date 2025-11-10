package ma.prodenta.service.modules.patient.api;

import java.util.List;
import ma.prodenta.mvc.dto.PatientDTO;

public interface PatientService {

    // Retourne les patients du jour transformés en DTO (nom, âge, date formatée)
    List<PatientDTO> getTodayPatientsAsDTO() throws Exception;
}
