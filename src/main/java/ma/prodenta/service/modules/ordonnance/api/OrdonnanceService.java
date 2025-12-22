package ma.prodenta.service.modules.ordonnance.api;

import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.RDV;
import ma.prodenta.service.common.Service;

import java.time.LocalDate;
import java.util.List;

public interface OrdonnanceService extends Service<Ordonnance,Integer> {
    public int totalOrdonnance(Ordonnance ord);
    public List<Medicament> medicamentsOrdonnance(Ordonnance ord);
    public List<Ordonnance> findByPatient(int idPatient);
    public List<Ordonnance> findByDate(LocalDate date);
}
