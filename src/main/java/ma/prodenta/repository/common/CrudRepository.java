package ma.prodenta.repository.common;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Utilisateur;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    List<T> findAll() throws Exception;

    boolean delete(Integer id);

    T findById(ID id) throws Exception;

    boolean create(T objet) throws SQLException;

    Utilisateur save(Utilisateur user);

    void update(T objet);

    boolean delete(T objet) throws SQLException;

    boolean deleteById(ID id) throws SQLException;

    // -------- Extras --------
    Optional<Antecedent> findByNom(String nom);
}
