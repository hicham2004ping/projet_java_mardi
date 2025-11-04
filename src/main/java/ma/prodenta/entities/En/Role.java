package ma.dentalTech.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private Integer idRole;
    private String libelle;
}
