package ma.prodenta.mvc.dto.patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.En.Antecedent;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient_Deatail_DTO {
    private int id;
    private String nom;
    private String prenom;
    private List<Antecedent> antecedents;
}
