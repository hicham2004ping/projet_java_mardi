package ma.dentalTech.entities.En;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medecin {
    private int idUser;
    private String specialite;
    private String agendaMensuel;
}
