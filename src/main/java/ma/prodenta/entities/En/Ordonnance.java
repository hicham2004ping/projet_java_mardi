package ma.dentalTech.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ordonnance implements Serializable {
    private Integer idOrd;
    private Date dateOrd;
    private Integer idDossier;
}
