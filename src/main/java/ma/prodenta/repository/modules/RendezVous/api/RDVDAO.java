package ma.prodenta.repository.modules.RendezVous.api;

import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.common.CrudRepository;

import java.util.Date;
import java.util.List;


public interface RDVDAO extends CrudRepository<RDV,Integer> {
    List<RDV> FindByDay(Date DateTime) throws Exception;
    boolean existsById(Integer id);
    Integer count();
    int get_last_id();
    public List<RDV> findByDossier(Integer idDossier) throws Exception;
    public RDV findByDossier1(Integer idDossier) throws Exception;
}
