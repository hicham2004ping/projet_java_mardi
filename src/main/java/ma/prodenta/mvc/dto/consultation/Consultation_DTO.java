package ma.prodenta.mvc.dto.consultation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation_DTO {
    public String nom_patient;
    public String prenom_patient;
    public LocalDate date;
    public String nom_medecin;
}
