package ma.prodenta.service.modules.rendezvous.api;
import ma.prodenta.entities.En.RDV;

import java.util.Date;
import java.util.List;

public interface RDVI {
    List<RDV> TrouveCrenau(Date date);
    List<Integer> TrouveCrenauLibre(Date date);
    void createRDV(RDV rdv);
    void updateRDV(RDV rdv);
    void deleteRDV(RDV rdv);
}
