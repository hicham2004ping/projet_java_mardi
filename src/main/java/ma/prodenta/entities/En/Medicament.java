package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicament implements Serializable {
    private Integer idMed;
    private String nom;
    private String laboratoire;
    private String type;
    private Boolean remboursable; // tinyint(1)
    private Double prixUnit;
    private String description;
    private Integer idForme;
}
