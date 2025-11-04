package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificat implements Serializable {
    private Integer idCert;
    private Date dateDebut;
    private Date dateFin;
    private String nature;
    private String noteMedecin;
    private Integer idDossier;
}
