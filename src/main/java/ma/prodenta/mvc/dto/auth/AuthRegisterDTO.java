package ma.prodenta.mvc.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegisterDTO {
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String cin;
    private String tel;
    private String login;
    private String motdepasse;
    private LocalDate dateNaissance;
    private Integer idSexe;
    private Integer idRole;
}
