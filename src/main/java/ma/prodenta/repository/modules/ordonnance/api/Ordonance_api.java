package ma.prodenta.repository.modules.ordonnance.api;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.repository.common.CrudRepository;
import java.sql.SQLException;
import java.util.List;

public interface Ordonance_api extends CrudRepository<Ordonnance,Integer> {
    public List<Medicament> find_all_medicament_in_ordonance(Ordonnance ordonance);
    public int Total_ordonance(Ordonnance ordonance);
    public List<Ordonnance> list_ordonances_dossier(int idDossier) throws Exception;
    List<Ordonnance> consulterOrdonnancesParConsultation(Integer idConsultation) throws SQLException;
    double calculerCoutTotal(Ordonnance ordonnance) throws SQLException;
    List<Prescription> list_Prescriptions (Ordonnance ordonnance) throws Exception;

}
