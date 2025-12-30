package ma.prodenta.mvc.dto.dossiermedical;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@AllArgsConstructor @Data @NoArgsConstructor

public class Dossier_Medical_vu_generale_DTO {
    private int idDossier;
    private String patient_nom;
    private String patient_prenom;
    private int total_conusltations;
    private int  total_ordonnance;
    private LocalDate date_creation;
}
