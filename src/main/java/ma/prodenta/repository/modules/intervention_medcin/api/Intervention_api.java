package ma.prodenta.repository.modules.intervention_medcin.api;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.Intervention;
import ma.prodenta.repository.common.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface Intervention_api extends CrudRepository<Intervention, Integer> {
    public int numero_intervention_par_dent();
    public int total_intervention();
    public List<Intervention> interventions_par_consultation(Consultation consultation) throws SQLException;
}
