package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicament implements Serializable {
    private Integer idMed;
    private String nom;
    private String laboratoire;
    private String type;
    private Boolean remboursable;
    private Double prixUnit;
    private String description;
    private Integer idForme;
}
