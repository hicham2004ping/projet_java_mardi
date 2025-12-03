package ma.prodenta.entities.En;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private LocalDate dateNaissance;
    private LocalDateTime lastLoginDate;
    private Integer idRole;


}
