package ma.prodenta.repository.modules.RendezVous.api;

import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.common.CrudRepository;

import java.util.Date;
import java.util.List;


public interface RDVDAO extends CrudRepository<RDV,Integer> {
    List<RDV> FindByDay(Date DateTime) throws Exception;
    boolean existsById(Integer id);
    Integer count();
}
