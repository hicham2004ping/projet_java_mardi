package ma.prodenta.entities.En;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ordonnance implements Serializable {
    private Long idOrd;
    private LocalDate dateOrd;
    private Integer idDossier;
    private int idconsultation;
}
