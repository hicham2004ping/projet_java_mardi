package ma.prodenta.mvc.dto.ordonnance;


import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdonnanceDTO {
    private Long idOrd;
    private LocalDate dateOrd;
    private Integer idDossier;
    private Integer idConsultation;
}
