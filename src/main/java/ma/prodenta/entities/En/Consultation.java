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
    private Date dateConsult;
    private String observationMedecin;
    private Integer idDossier;
    private Integer idStatut;
    private int id_medecin;
    private int id_rdv;
}
