package ma.prodenta.service.modules.auth;

import ma.prodenta.common.util.PasswordUtil;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.auth.UserDAO;

//authentification
public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    public Utilisateur login(String login, String motdepasse) {
        if (login == null || motdepasse == null) return null;
        Utilisateur user = userDAO.findByUsername(login);
        if (user == null) return null;
        boolean ok = PasswordUtil.verifyPassword(motdepasse, user.getPasswordHash());
        return ok ? user : null;
    }
}
