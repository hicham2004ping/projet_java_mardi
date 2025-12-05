package ma.prodenta.entities.En;
import lombok.*;
import java.util.Date;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation implements Serializable {
    private Integer idConsult;
    private Date dateConsult; // date
    private String observationMedecin;
    private Integer idDossier;
    private Integer idStatut;
}
