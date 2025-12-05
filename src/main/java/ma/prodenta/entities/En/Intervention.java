package ma.prodenta.entities.En;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class Intervention {
  private  int id;
  private int numero_dent;
  private int prix_patient;
  private Acte acte;
}
