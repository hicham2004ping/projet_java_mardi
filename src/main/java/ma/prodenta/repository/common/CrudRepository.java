package ma.prodenta.repository.common;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.RDV;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    List<T> findAll() throws Exception;

    T findById(ID id) throws Exception;

    void create(T objet) throws Exception;

    void update(T objet) throws Exception;

    void delete(T objet)  throws Exception;

    void deleteById(ID id) throws Exception;

    // -------- Extras --------
    Optional<Antecedent> findByNom(String nom);
}
