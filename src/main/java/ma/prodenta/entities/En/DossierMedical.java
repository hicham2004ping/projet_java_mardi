package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierMedical implements Serializable {
    private Integer idDossier;
    private Date dateCreation;
    private Integer idPatient;
    private Integer idMedecin;
}
