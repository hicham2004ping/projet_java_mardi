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

    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private LocalDate dateNaissance;
    private LocalDateTime dateCreation;
    private Sexe sexe;
    private Assurance assurance;
    private List<Antecedent> antecedents = null;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient that = (Patient) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
            Patient {
              id = %d,
              nom = '%s',
              prenom = '%s',
              adresse = '%s',
              telephone = '%s',
              email = '%s',
              dateNaissance = %s,
              dateCreation = %s,
              sexe = %s,
              assurance = %s,
              antecedentsCount = %d
            }
            """.formatted(
                id,
                nom,
                prenom,
                adresse,
                telephone,
                email,
                dateNaissance,
                dateCreation,
                sexe,
                assurance,
                antecedents == null ? 0 : antecedents.size()
        );
    }



    public int compareTo(Patient other) {
        return id.compareTo(other.id);
    }
}