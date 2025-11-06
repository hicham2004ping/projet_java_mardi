package ma.prodenta.entities.En;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medecin {
    private int idUser;
    private String specialite;
    private String agendaMensuel;
}
