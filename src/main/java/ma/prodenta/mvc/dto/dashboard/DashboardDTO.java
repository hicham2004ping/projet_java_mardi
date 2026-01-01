package ma.prodenta.mvc.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {
    private Integer idDashboard;
    private Date dateDebut;
    private Date dateFin;
    private Integer nbPatients;
    private Integer nbActes;
    private Double totalRecettes;
    private Double totalDepenses;
}
