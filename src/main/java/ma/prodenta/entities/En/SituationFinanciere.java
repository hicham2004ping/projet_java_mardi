package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituationFinanciere implements Serializable {
    private Integer idSF;
    private Double totalActes;
    private Double totalPaye;
    private Double credit;
    private String statut; // enum('payee','non payee','en attente','annulé')
    private String enPromo; // enum('Oui','Non')
    private Integer idPatient;
}
