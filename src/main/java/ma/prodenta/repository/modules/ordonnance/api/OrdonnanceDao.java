package ma.prodenta.repository.modules.ordonnance.api;

import ma.prodenta.entities.En.Ordonnance;
import java.util.List;

public interface OrdonnanceDao {
    void create(Ordonnance ordonnance) throws Exception;
    void update(Ordonnance ordonnance) throws Exception;
    void delete(int idOrdonnance) throws Exception;
    Ordonnance findById(int idOrdonnance) throws Exception;
    List<Ordonnance> findAll() throws Exception;
}
