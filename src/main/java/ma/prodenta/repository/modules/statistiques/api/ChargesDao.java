package ma.prodenta.repository.modules.statistiques.api;

import ma.prodenta.entities.En.Charges;
import java.util.List;

public interface ChargesDao {
    void create(Charges charge) throws Exception;
    Charges findById(Integer id) throws Exception;
    List<Charges> findAll() throws Exception;
    void update(Charges charge) throws Exception;
    void delete(Integer id) throws Exception;
}
