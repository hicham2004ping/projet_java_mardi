package ma.dentalTech.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facture implements Serializable {
    private Integer idFact;
    private Double total;
    private Double totalpaye;
    private Double reste;
    private String statut; // enum ('payee','non payee','en attente','annulé')
    private Date dateFact; // datetime
    private Integer idSF; // situationfinanciere id
}
