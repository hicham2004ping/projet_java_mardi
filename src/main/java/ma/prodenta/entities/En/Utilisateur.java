package ma.prodenta.entities.En;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    private int idUser;
    private String nom;
    private String email;
    private String adresse;
    private String cin;
    private String tel;
    private Integer idSexe;
    private String login;
    private String motdepasse;
    private Date dateNaissance;
    private Date lastLoginDate;
    private Integer idRole;
}
