package ma.prodenta.repository.common;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Utilisateur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    List<T> findAll() throws Exception;

    T findById(ID id) throws Exception;

    boolean create(T objet) throws SQLException, IOException;


    void update(T objet) throws SQLException, IOException,Exception;

    boolean delete(T objet) throws SQLException,Exception;

    boolean deleteById(ID id) throws SQLException,Exception;

    Optional<Antecedent> findByNom(String nom);
}
