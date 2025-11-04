package ma.prodenta.entities.En;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabinetMedical implements Serializable {
    private Integer idCabinet;
    private String nom;
    private String email;
    private String logo;
    private String adresse;
    private String tel1;
    private String tel2;
    private String siteweb;
    private String description;
    private String instagram;
    private String facebook;
}
