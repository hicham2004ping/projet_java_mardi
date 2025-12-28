package ma.prodenta.mvc.dto.intervention;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Intervention_DTO {
    private int id;
    private String nom_patient;
    private String prenom_patient;
    private LocalDate date;
    private String libelle_acte;
    private int prix;
}
