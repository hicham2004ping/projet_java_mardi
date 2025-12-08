package ma.prodenta.entities.En;
import lombok.*;
import java.time.LocalDate;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DossierMedical implements Serializable {

    private Integer idDossier;
    private LocalDate dateCreation;
    private Integer idPatient;
    private Integer idMedecin;
}
