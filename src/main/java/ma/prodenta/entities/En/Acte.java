package ma.prodenta.entities.En;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Acte {
    private int id;
    private String categorie;
    private String libelle;
    private double prix_de_base;
}
