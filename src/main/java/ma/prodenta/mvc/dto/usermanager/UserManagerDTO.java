package ma.prodenta.mvc.dto.usermanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserManagerDTO {
    private Integer idUser;
    private String username;
    private String role;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}
