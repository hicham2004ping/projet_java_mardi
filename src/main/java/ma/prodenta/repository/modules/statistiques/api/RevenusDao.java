package ma.prodenta.repository.modules.statistiques.api;

import ma.prodenta.entities.En.Revenus;
import java.util.List;

public interface RevenusDao {
    void create(Revenus revenu) throws Exception;
    Revenus findById(Integer id) throws Exception;
    List<Revenus> findAll() throws Exception;
    void update(Revenus revenu) throws Exception;
    void delete(Integer id) throws Exception;
}
