package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {
    private Integer idPatient;
    private String nom;
    private Date dateNaissance;
    private String adresse;
    private String telephone;
    private Integer idSexe;
    private Integer idAssurance;
}
