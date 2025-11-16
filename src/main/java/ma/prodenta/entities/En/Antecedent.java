package ma.prodenta.entities.En;

import lombok.*;

import java.util.List;

import ma.prodenta.entities.Enum.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Antecedent {

    private Long IdAntecedent;
    private String nom;
    private String categorie;
    private NiveauRisque niveauRisque;

    private List<Patient> patients;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Antecedent that = (Antecedent) o;
        return getIdAntecedent() != null && getIdAntecedent().equals(that.getIdAntecedent());
    }

    @Override
    public int hashCode() {
        return getIdAntecedent() != null ? getIdAntecedent().hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Antecedent {
          id = %d,
          nom = '%s',
          categorie = %s,
          niveauRisque = %s,
          patientsCount = %d
        }
        """.formatted(
                getIdAntecedent(),
                nom,
                categorie,
                niveauRisque,
                patients == null ? 0 : patients.size()
        );
    }


}

