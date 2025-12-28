package ma.prodenta.mvc.dto.rdv;

import lombok.*;
import java.util.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RDVDTO {
    private Integer idRDV;
    private Date dateRDV;
    private Time heure;
    private String motif;
    private String noteMedecin;
    private Integer idDossier;
}

