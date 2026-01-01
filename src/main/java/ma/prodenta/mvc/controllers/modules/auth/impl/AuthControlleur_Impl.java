package ma.prodenta.mvc.controllers.modules.auth.impl;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.mvc.controllers.modules.auth.api.AuthControlleur_Api;
import ma.prodenta.service.modules.auth.impl.AuthServiceImpl;
import ma.prodenta.common.exceptions.*;
import java.util.List;

public class AuthControlleur_Impl implements AuthControlleur_Api {

    private AuthServiceImpl authService;

    public AuthControlleur_Impl() {
        try {
            this.authService = Application_contexte.getAuthServiceImpl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Utilisateur authenticate(String login, String motdepasse) throws Exception {
        try {
            if (login == null || login.isEmpty()) {
                throw new ArgumentException("le login ne peut pas etre vide");
            }
            if (motdepasse == null || motdepasse.isEmpty()) {
                throw new ArgumentException("le mot de passe ne peut pas etre vide");
            }
            return authService.authenticate(login, motdepasse);
        } catch (AuthException | ArgumentException | ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Utilisateur register(Utilisateur utilisateur) throws Exception {
        try {
            if (utilisateur == null) {
                throw new ArgumentException("l'utilisateur ne peut pas etre null");
            }
            if (utilisateur.getLogin() == null || utilisateur.getLogin().isEmpty()) {
                throw new ArgumentException("le login est requis");
            }
            if (utilisateur.getMotdepasse() == null || utilisateur.getMotdepasse().isEmpty()) {
                throw new ArgumentException("le mot de passe est requis");
            }
            if (utilisateur.getEmail() == null || utilisateur.getEmail().isEmpty()) {
                throw new ArgumentException("l'email est requis");
            }
            return authService.register(utilisateur);
        } catch (EmailExisteException | ArgumentException | ErreurCreationException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Utilisateur findByLogin(String login) throws Exception {
        try {
            if (login == null || login.isEmpty()) {
                throw new ArgumentException("le login ne peut pas etre vide");
            }
            return authService.findByLogin(login);
        } catch (ArgumentException | ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Utilisateur findByEmail(String email) throws Exception {
        try {
            if (email == null || email.isEmpty()) {
                throw new ArgumentException("l'email ne peut pas etre vide");
            }
            return authService.findByEmail(email);
        } catch (ArgumentException | ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean existsByLogin(String login) throws Exception {
        try {
            if (login == null || login.isEmpty()) {
                throw new ArgumentException("le login ne peut pas etre vide");
            }
            return authService.existsByLogin(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean existsByEmail(String email) throws Exception {
        try {
            if (email == null || email.isEmpty()) {
                throw new ArgumentException("l'email ne peut pas etre vide");
            }
            return authService.existsByEmail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public long countUsers() throws Exception {
        try {
            return authService.countUsers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Utilisateur> getAllUsers() throws Exception {
        try {
            return authService.getAllUsers();
        } catch (ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateUser(Utilisateur utilisateur) throws Exception {
        try {
            if (utilisateur == null) {
                throw new ArgumentException("l'utilisateur ne peut pas etre null");
            }
            if (utilisateur.getIdUser() <= 0) {
                throw new ArgumentException("l'id de l'utilisateur est invalide");
            }
            authService.updateUser(utilisateur);
        } catch (ArgumentException | ErreurCreationException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deleteUser(Integer idUser) throws Exception {
        try {
            if (idUser == null || idUser <= 0) {
                throw new ArgumentException("l'id de l'utilisateur est invalide");
            }
            return authService.deleteUser(idUser);
        } catch (ArgumentException | ErreurSuppressionException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
