package ma.prodenta.repository.modules.medicament.api;

import ma.prodenta.entities.En.Medicament;
import java.util.List;

public interface MedicamentDao {
    void create(Medicament medicament) throws Exception;
    void update(Medicament medicament) throws Exception;
    void delete(int idMed) throws Exception;
    Medicament findById(int idMed) throws Exception; // ✅ f minuscule
    List<Medicament> findAll() throws Exception;
}

