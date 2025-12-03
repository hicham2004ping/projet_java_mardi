package ma.prodenta.repository.modules.patient.api;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.CategorieAntecedent;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AntecedentDao extends CrudRepository<Antecedent, Long> {

    Optional<Antecedent> findByNom(String nom);
    List<Antecedent> findByCategorie(CategorieAntecedent categorie);
    List<Antecedent> findByNiveauRisque(NiveauRisque niveau);
    boolean existsById(Long id);
    long count();
    List<Antecedent> findPage(int limit, int offset);

    // ---- Navigation inverse ----
    List<Patient> getPatientsHavingAntecedent(Long antecedentId);
}
