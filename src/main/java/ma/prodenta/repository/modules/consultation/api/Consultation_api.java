package ma.prodenta.repository.modules.consultation.api;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.repository.common.CrudRepository;

public interface Consultation_api extends CrudRepository<Consultation,Integer> {
    public int total_consultations();

}
