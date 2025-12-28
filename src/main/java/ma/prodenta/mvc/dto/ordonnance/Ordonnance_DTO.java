package ma.prodenta.mvc.dto.ordonnance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ordonnance_DTO {
    private int id;
    private String nom_patient;
    private String prenom_patient;
    private LocalDate date;
}
