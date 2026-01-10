package ma.prodenta.mvc.dto.rdv;

import lombok.*;
import ma.prodenta.entities.En.RDV;
import java.util.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RDVDTO {
    private Integer idRDV;
    private Date dateRDV;
    private Time heure;
    private String motif;
    private String noteMedecin;
    private Integer idDossier;

    public static RDVDTO rdvToDTO(RDV rdv) {
        if (rdv == null) return null;
        return RDVDTO.builder()
                .idRDV(rdv.getIdRDV())
                .dateRDV(rdv.getDateRDV())
                .heure(rdv.getHeure())
                .motif(rdv.getMotif())
                .noteMedecin(rdv.getNoteMedecin())
                .idDossier(rdv.getIddossier())
                .build();
    }

    public RDV toEntity() {
        return RDV.builder()
                .idRDV(this.idRDV)
                .dateRDV(this.dateRDV)
                .heure(this.heure)
                .motif(this.motif)
                .noteMedecin(this.noteMedecin)
                .iddossier(this.idDossier)
                .build();
    }
}

