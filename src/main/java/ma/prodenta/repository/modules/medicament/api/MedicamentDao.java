package ma.prodenta.repository.modules.medicament.api;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.repository.common.CrudRepository;

import java.util.List;

public interface MedicamentDao extends  CrudRepository<Medicament,Long>  {
}

