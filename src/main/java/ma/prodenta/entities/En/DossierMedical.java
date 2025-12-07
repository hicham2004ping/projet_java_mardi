package ma.prodenta.entities.En;
import lombok.*;
import java.time.LocalDate;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DossierMedical implements Serializable {
    private Integer idDossier;
    private LocalDate dateCreation;
    private Integer idPatient;
    private Integer idMedecin;
    public void setIdDossier(long aLong) {
    }

    public long getPatientId() {
        return 0;
    }

    public String getAllergies() {
        return "";
    }

    public String getAntecedents() {
        return "";
    }

    public String getNotes() {
        return "";
    }

    public String getDossier() {
        return "";
    }

    public void setPatientId(long patientId) {
    }

    public void setAllergies(String allergies) {
    }

    public void setAntecedents(String antecedents) {
    }

    public void setNotes(String notes) {
    }

    public Long getId() {
        return 0L;
    }

    public void setId(Long id) {
    }
}
