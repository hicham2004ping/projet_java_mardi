package ma.prodenta.repository.modules.statistiques.api;

import ma.prodenta.entities.En.Revenus;
import ma.prodenta.repository.common.CrudRepository;

import java.util.List;

public interface RevenusDao extends CrudRepository<Revenus, Long> {
    public Long get_last_id();

}
