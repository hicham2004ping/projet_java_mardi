package ma.dentalTech.entities.En;

import lombok.*;
import java.util.Date;
import java.sql.Time;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RDV implements Serializable {
    private Integer idRDV;
    private Date dateRDV;
    private Time heure;
    private String motif;
    private String noteMedecin;
    private Integer idPatient;
}
