package ma.prodenta.mvc.dto.consultation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.En.Consultation;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {
    public String nom_patient;
    public String prenom_patient;
    public LocalDate date;
    public String nom_medecin;
}
