package ma.prodenta.mvc.dto.antecedent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.Enum.NiveauRisque;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Antecedent_DTO {
    private int id;
    String categorie;
    NiveauRisque niveau;
}
