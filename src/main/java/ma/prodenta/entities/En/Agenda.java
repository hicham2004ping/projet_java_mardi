// entities/En/Agenda.java
package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agenda implements Serializable {
    private Integer idAgenda;
    private Integer idMedecin;
    private Integer idPatient;
    private Date dateDebut;
    private Date dateFin;
    private String statut; // par ex : planifie, en_cours, termine, annule
    private String note;
}

