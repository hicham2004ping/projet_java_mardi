package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Charges implements Serializable {
    private Long idCharge;
    private String titre;
    private String description;
    private Double montant;
    private Date dateCharge;
    private Integer idCabinet;
}
