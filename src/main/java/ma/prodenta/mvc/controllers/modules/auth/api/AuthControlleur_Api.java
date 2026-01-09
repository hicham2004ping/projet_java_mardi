package ma.prodenta.mvc.controllers.modules.auth.api;

import ma.prodenta.entities.En.Utilisateur;
import java.util.List;

public interface AuthControlleur_Api {
    Utilisateur authenticate(String login, String motdepasse) throws Exception;
    Utilisateur register(Utilisateur utilisateur) throws Exception;
    Utilisateur findByLogin(String login) throws Exception;
    Utilisateur findByEmail(String email) throws Exception;
    boolean existsByLogin(String login) throws Exception;
    boolean existsByEmail(String email) throws Exception;
    long countUsers() throws Exception;
    List<Utilisateur> getAllUsers() throws Exception;
    void updateUser(Utilisateur utilisateur) throws Exception;
    boolean deleteUser(Integer idUser) throws Exception;
}
