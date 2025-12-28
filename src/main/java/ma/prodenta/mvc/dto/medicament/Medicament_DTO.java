package ma.prodenta.mvc.dto.medicament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicament_DTO {
    private String nom;
    private String forme;
    private String type;
    private double prix;
}
