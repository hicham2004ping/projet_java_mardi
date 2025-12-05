package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dashboard implements Serializable {
    private Integer idDashboard;
    private Date dateDebut;
    private Date dateFin;
    private Integer nbPatients;
    private Integer nbActes;
    private Double totalRecettes;
    private Double totalDepenses;
}

