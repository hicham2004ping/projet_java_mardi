package ma.prodenta.repository.modules.intervention_medcin.api;

import ma.prodenta.entities.En.Intervention;
import ma.prodenta.repository.common.CrudRepository;

public interface Intervention_api extends CrudRepository<Intervention, Integer> {
    public int numero_intervention_par_dent();
    public int total_intervention();
}
