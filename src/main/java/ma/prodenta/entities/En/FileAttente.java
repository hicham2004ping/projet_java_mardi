package ma.prodenta.entities.En;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileAttente {
    private Integer idFileAttente;
    private Integer idDossier;
    private LocalDate dateFile;
    private String statut; // 'En attente', 'En consultation', 'Terminé'
    private Integer position;
    private LocalDateTime dateArrivee;
}
