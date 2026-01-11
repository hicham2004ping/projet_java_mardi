// repository/modules/Caisse/api/ChargesRepository.java
package ma.prodenta.repository.modules.Caisse.api;

import ma.prodenta.entities.En.Charges;

import java.util.List;

public interface ChargesRepository {
    Charges findById(Integer id) throws Exception;
    List<Charges> findAll() throws Exception;
    Charges save(Charges charge) throws Exception;
    Charges update(Charges charge) throws Exception;
    void delete(Integer id) throws Exception;
}
