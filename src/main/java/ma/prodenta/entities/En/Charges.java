package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Charges implements Serializable {
    private Integer idCharge;
    private String titre;
    private String description;
    private Double montant;
    private Date dateCharge; // datetime in DB
    private Integer idCabinet;
}
