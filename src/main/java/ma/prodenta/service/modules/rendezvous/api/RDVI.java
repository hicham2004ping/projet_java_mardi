package ma.prodenta.service.modules.rendezvous.api;
import ma.prodenta.entities.En.RDV;
import ma.prodenta.service.common.Service;

import java.util.Date;
import java.util.List;

public interface RDVI extends Service<RDV,Integer> {
    List<RDV> TrouveCrenau(Date date);
    List<Integer> TrouveCrenauLibre(Date date) throws Exception;
}
