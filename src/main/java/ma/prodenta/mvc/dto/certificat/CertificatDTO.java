package ma.prodenta.mvc.dto.certificat;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificatDTO {
    private Integer idCert;
    private Date dateDebut;
    private Date dateFin;
    private String nature;
    private String noteMedecin;

    private Integer idDossier;
    private Integer idConsult;
}