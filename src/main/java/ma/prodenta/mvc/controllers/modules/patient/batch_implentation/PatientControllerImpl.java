package ma.prodenta.mvc.controllers.modules.patient.batch_implentation;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.mvc.controllers.modules.patient.api.PatientController;
import ma.prodenta.service.modules.patient.api.PatientService;
@Data
public class PatientControllerImpl {
    private String nom;
    private String adresse;
    private List<String> listeAntecedents;
    private  String telephone;
    private String assurance;
    private LocalDate dateNaissance;
    public PatientControllerImpl(String nom, String adresse, String telephone, String assurance, List<String> listeAntecedents,LocalDate dateNaissance) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.assurance = assurance;
        this.listeAntecedents = listeAntecedents;
    }

}
