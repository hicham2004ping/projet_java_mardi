/*
package ma.prodenta.mvc.controllers.modules.patient.swing_implementation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.mvc.controllers.modules.patient.api.PatientController;
import ma.prodenta.mvc.dto.PatientDTO;
import ma.prodenta.mvc.ui.modules.patient.PatientView;
import ma.prodenta.service.modules.patient.api.PatientService;

@Data @AllArgsConstructor @NoArgsConstructor
public class PatientControllerImpl implements PatientController {

    private PatientService service;

    @Override
    public void showRecentPatients() {
        List<PatientDTO> dtos = service.getTodayPatientsAsDTO();
        PatientView.showAsync(dtos);
    }
}
*/
