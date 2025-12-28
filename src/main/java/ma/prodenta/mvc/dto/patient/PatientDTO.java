package ma.prodenta.mvc.dto.patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PatientDTO {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String dateCreationFormatee;
}




