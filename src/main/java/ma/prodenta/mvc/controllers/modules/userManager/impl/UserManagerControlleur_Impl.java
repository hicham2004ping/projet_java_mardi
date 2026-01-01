package ma.prodenta.mvc.controllers.modules.userManager.impl;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.UserManager;
import ma.prodenta.mvc.controllers.modules.userManager.api.UserManagerControlleur_Api;
import ma.prodenta.service.modules.usermanager.impl.UserManagerServiceImpl;
import ma.prodenta.common.exceptions.*;
import java.util.List;

public class UserManagerControlleur_Impl implements UserManagerControlleur_Api {

    private UserManagerServiceImpl userManagerService;

    public UserManagerControlleur_Impl() {
        try {
            this.userManagerService = Application_contexte.getUserManagerServiceImpl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public UserManager createUser(UserManager userManager) throws Exception {
        try {
            if (userManager == null) {
                throw new ArgumentException("l'utilisateur ne peut pas etre null");
            }
            if (userManager.getUsername() == null || userManager.getUsername().isEmpty()) {
                throw new ArgumentException("le username est requis");
            }
            if (userManager.getPasswordHash() == null || userManager.getPasswordHash().isEmpty()) {
                throw new ArgumentException("le mot de passe est requis");
            }
            return userManagerService.createUser(userManager);
        } catch (ArgumentException | ErreurCreationException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public UserManager updateUser(UserManager userManager) throws Exception {
        try {
            if (userManager == null) {
                throw new ArgumentException("l'utilisateur ne peut pas etre null");
            }
            if (userManager.getIdUser() == null || userManager.getIdUser() <= 0) {
                throw new ArgumentException("l'id de l'utilisateur est invalide");
            }
            return userManagerService.updateUser(userManager);
        } catch (ArgumentException | ErreurCreationException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteUser(Integer idUser) throws Exception {
        try {
            if (idUser == null || idUser <= 0) {
                throw new ArgumentException("l'id de l'utilisateur est invalide");
            }
            userManagerService.deleteUser(idUser);
        } catch (ArgumentException | ErreurSuppressionException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public UserManager findById(Integer idUser) throws Exception {
        try {
            if (idUser == null || idUser <= 0) {
                throw new ArgumentException("l'id de l'utilisateur est invalide");
            }
            return userManagerService.findById(idUser);
        } catch (ArgumentException | ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public UserManager findByUsername(String username) throws Exception {
        try {
            if (username == null || username.isEmpty()) {
                throw new ArgumentException("le username ne peut pas etre vide");
            }
            return userManagerService.findByUsername(username);
        } catch (ArgumentException | ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UserManager> findAll() throws Exception {
        try {
            return userManagerService.findAll();
        } catch (ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public UserManager authenticateUser(String username, String password) throws Exception {
        try {
            if (username == null || username.isEmpty()) {
                throw new ArgumentException("le username ne peut pas etre vide");
            }
            if (password == null || password.isEmpty()) {
                throw new ArgumentException("le mot de passe ne peut pas etre vide");
            }
            return userManagerService.authenticateUser(username, password);
        } catch (AuthException | ArgumentException | ErreurLectureException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean activateUser(Integer idUser) throws Exception {
        try {
            if (idUser == null || idUser <= 0) {
                throw new ArgumentException("l'id de l'utilisateur est invalide");
            }
            return userManagerService.activateUser(idUser);
        } catch (ArgumentException | ErreurCreationException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deactivateUser(Integer idUser) throws Exception {
        try {
            if (idUser == null || idUser <= 0) {
                throw new ArgumentException("l'id de l'utilisateur est invalide");
            }
            return userManagerService.deactivateUser(idUser);
        } catch (ArgumentException | ErreurCreationException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public long countActiveUsers() throws Exception {
        try {
            return userManagerService.countActiveUsers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
