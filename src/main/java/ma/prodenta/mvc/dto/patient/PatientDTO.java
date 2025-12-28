package ma.prodenta.mvc.dto.patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.En.Patient;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PatientDTO {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate date_naissance;

    public PatientDTO patientParseDto(Patient p){
        PatientDTO patientDTO=new PatientDTO();
        patientDTO.setId(p.getId());
        patientDTO.setNom(p.getNom());
        patientDTO.setPrenom(p.getPrenom());
        patientDTO.setDate_naissance(p.getDateNaissance());
        return patientDTO;
    }
}





