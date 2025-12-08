package ma.prodenta.repository.modules.ordonnance.api;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.repository.common.CrudRepository;

import java.util.List;

public interface Ordonance_api extends CrudRepository<Ordonnance,Integer> {
    public List<Medicament> find_all_medicament_in_ordonance(Ordonnance ordonance);
    public int Total_ordonance(Ordonnance ordonance);
}
