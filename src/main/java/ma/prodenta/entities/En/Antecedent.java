package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Antecedent implements Serializable {
    private Integer idAntecedent;
    private String nom;
    private String categorie;
    private Integer idRisque;
    private Integer idPatient;
}
