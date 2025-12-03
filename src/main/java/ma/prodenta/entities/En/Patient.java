package ma.prodenta.entities.En;
import lombok.*;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;
import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Patient { //implements Comparable<Patient>{
    private String prenom ;
    private int  id;
    private String nom;
    private LocalDate dateNaissance;
    private String adresse;
    private String email ;
    private String telephone;
    private Sexe sexe;
    private Assurance assurance;
    private List<Antecedent> antecedents = null;
}