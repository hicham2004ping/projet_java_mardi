package ma.prodenta.repository.modules.statut_consultation.api;
import ma.prodenta.entities.En.Staut_consultation;
import ma.prodenta.repository.common.CrudRepository;

public interface Statut_consultation_api extends CrudRepository<Staut_consultation, Integer> {
    public Staut_consultation findBYnom(String libelle);
}
