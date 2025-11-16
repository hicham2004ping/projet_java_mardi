package ma.prodenta.repository.common;
import ma.prodenta.entities.En.Antecedent;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    List<T> findAll() throws Exception;

    T findById(ID id) throws Exception;

    void create(T patient);

    void update(T patient);

    void delete(T patient);

    void deleteById(ID id);

    // -------- Extras --------
    Optional<Antecedent> findByNom(String nom);
}
