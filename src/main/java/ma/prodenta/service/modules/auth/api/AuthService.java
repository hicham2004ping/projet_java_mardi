package ma.prodenta.service.modules.auth.api;

import ma.prodenta.entities.En.Utilisateur;
import java.util.List;

public interface AuthService {
    Utilisateur authenticate(String login, String motdepasse) throws Exception;
    Utilisateur register(Utilisateur utilisateur) throws Exception;
    Utilisateur findByLogin(String login) throws Exception;
    Utilisateur findByEmail(String email) throws Exception;
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    long countUsers();
    List<Utilisateur> getAllUsers() throws Exception;
    void updateUser(Utilisateur utilisateur) throws Exception;
    boolean deleteUser(Integer idUser) throws Exception;
}
