package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff implements Serializable {
    private Integer idStaff;
    private Double salaire;
    private Double prime;
    private Date dateRecrutement;
    private Integer soldeConge;
    private Integer idMedecin;
    private Integer idSecretaire;
}
