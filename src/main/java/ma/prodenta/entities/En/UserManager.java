package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserManager implements Serializable {
    private Integer idUser;
    private String username;
    private String passwordHash;
    private String role;      // ADMIN, MEDECIN, SECRETAIRE...
    private Boolean actif;
    private Date dateCreation;
}
