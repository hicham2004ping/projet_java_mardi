package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements Serializable {
    private Integer idRole;
    private String libelle;
}
