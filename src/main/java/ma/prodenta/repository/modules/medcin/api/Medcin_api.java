package ma.prodenta.repository.modules.medcin.api;
import ma.prodenta.entities.En.Medecin;
import ma.prodenta.entities.En.Prescription;
import java.util.List;
import ma.prodenta.repository.common.CrudRepository;
public interface Medcin_api extends CrudRepository<Medecin,Integer> {
    public int total_dossier_mediceaux(Medecin medecin);
    public List<Prescription> all_prescriptions(Medecin medecin);

}
