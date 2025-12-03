package ma.prodenta.entities.En;

import lombok.*;

import java.util.List;

import ma.prodenta.entities.Enum.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Antecedent {
    private int IdAntecedent;
    private String nom;
    private String categorie;
    private NiveauRisque niveauRisque;
    private List<Patient> patients=null;
}

