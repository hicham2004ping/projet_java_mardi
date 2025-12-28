package ma.prodenta.mvc.dto.prescription;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Medicament_DTO {
    private String nom_patient;
    private String prenom_patient;
    private String libelle_medicament;
    private String frequence;
}
