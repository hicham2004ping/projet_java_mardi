package ma.prodenta.repository.modules.statistiques.api;

import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.common.CrudRepository;

import java.util.List;

public interface ChargesDao extends CrudRepository<Charges, Long> {
    public Long get_last_id();
}
