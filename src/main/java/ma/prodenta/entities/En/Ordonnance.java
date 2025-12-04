package ma.prodenta.entities.En;
import lombok.*;
import java.util.Date;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ordonnance implements Serializable {
    private Long idOrd;
    private Date dateOrd;
    private Integer idDossier;
}
