package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revenus implements Serializable {
    private Integer idRev;
    private String type;
    private String description;
    private Double montant;
    private Date dateRev; // datetime
    private Integer idCabinet;
}
