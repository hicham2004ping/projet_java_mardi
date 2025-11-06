package ma.prodenta.entities.En;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Secretaire {
    private int idUser;
    private String numCNSS;
    private Double commission;
}
