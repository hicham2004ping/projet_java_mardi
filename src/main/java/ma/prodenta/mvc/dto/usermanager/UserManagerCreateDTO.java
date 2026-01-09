package ma.prodenta.mvc.dto.usermanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserManagerCreateDTO {
    private String username;
    private String passwordHash;
    private String role;
    private Boolean actif;
}
