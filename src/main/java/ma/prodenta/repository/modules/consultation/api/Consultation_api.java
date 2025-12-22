package ma.prodenta.repository.modules.consultation.api;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.repository.common.CrudRepository;

import java.util.List;

public interface Consultation_api extends CrudRepository<Consultation,Integer> {
    public int total_consultations();
    public int get_last_id();
    public List<Consultation> findByRdv(Integer idRdv) throws Exception;

}
