package ma.prodenta.mvc.dto.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.En.Agenda;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDTO {
    private Integer idAgenda;
    private Integer idMedecin;
    private Integer idPatient;
    private Date dateDebut;
    private Date dateFin;
    private String statut;
    private String note;

    // Helper fields for display
    private String nomPatient;
    private String nomMedecin;

    public static AgendaDTO fromEntity(Agenda agenda) {
        if (agenda == null)
            return null;
        return AgendaDTO.builder()
                .idAgenda(agenda.getIdAgenda())
                .idMedecin(agenda.getIdMedecin())
                .idPatient(agenda.getIdPatient())
                .dateDebut(agenda.getDateDebut())
                .dateFin(agenda.getDateFin())
                .statut(agenda.getStatut())
                .note(agenda.getNote())
                .build();
    }

    public Agenda toEntity() {
        return Agenda.builder()
                .idAgenda(this.idAgenda)
                .idMedecin(this.idMedecin)
                .idPatient(this.idPatient)
                .dateDebut(this.dateDebut)
                .dateFin(this.dateFin)
                .statut(this.statut)
                .note(this.note)
                .build();
    }
}
