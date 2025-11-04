package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription implements Serializable {
    private Integer idPr;
    private Integer quantite;
    private String frequence;
    private Integer dureeEnJours;
    private Integer idOrd;
    private Integer idMed;
}
