package ma.prodenta.mvc.dto.dossiermedical;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@AllArgsConstructor @Data @NoArgsConstructor

public class Dossier_Medical_vu_generale_DTO {
    private String patient_email;
    private String patient_nom;
    private int patientId;
    private int total_conusltations;
    private int  total_ordonnance;
    private String total;
    private LocalDate date_premier_consultation;
}
