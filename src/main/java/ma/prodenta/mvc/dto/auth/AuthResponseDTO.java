package ma.prodenta.mvc.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private Integer idUser;
    private String nom;
    private String email;
    private String login;
    private String message;
    private boolean success;
}
