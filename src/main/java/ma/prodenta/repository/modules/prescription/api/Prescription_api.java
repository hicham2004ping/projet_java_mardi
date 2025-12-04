package ma.prodenta.repository.modules.prescription.api;

import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.repository.common.CrudRepository;

public interface Prescription_api extends CrudRepository<Prescription,Integer> {
    public int total_prescriptions(Ordonnance ordonance);

}
