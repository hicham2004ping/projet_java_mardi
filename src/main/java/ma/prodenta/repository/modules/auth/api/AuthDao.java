package ma.prodenta.repository.modules.auth.api;

import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.common.CrudRepository;

import java.util.Optional;

public interface AuthDao extends CrudRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByLogin(String login) throws Exception;
    Optional<Utilisateur> findByEmail(String email) throws Exception;

    // Auth verification
    Optional<Utilisateur> login(String login, String motdepasse) throws Exception;
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);

    long count();
}
