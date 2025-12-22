package ma.prodenta.service.modules.ordonnance.api;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Prescription;
import javax.naming.ldap.PagedResultsControl;
import java.time.LocalDate;
import java.util.List;

public interface Ordonnance_Service_api {

    List<Ordonnance> consulterOrdonnancesParDossier(Integer idDossier) throws Exception;

    List<Ordonnance> consulterOrdonnancesParConsultation(Integer idConsultation) throws Exception;

    List<Medicament> listerMedicamentsPrescrits(Ordonnance ordonnance) throws Exception;

    double calculerCoutTotal(Ordonnance ordonnance) throws Exception;

    List<Prescription> list_Prescriptions (Ordonnance ordonnance) throws Exception;


}
