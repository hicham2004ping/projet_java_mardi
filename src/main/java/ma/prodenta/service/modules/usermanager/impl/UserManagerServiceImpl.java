package ma.prodenta.service.modules.usermanager.impl;

import ma.prodenta.common.exceptions.ValidationException;
import ma.prodenta.common.util.PasswordUtil;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.UserManager;
import ma.prodenta.repository.modules.userManager.implementation.UserManagerRepositoryImpl;
import ma.prodenta.service.modules.usermanager.api.UserManagerService;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class UserManagerServiceImpl implements UserManagerService {

    @Override
    public UserManager createUser(UserManager userManager) throws Exception {
        if (userManager == null) {
            throw new ValidationException("L'utilisateur ne peut pas être null");
        }

        if (userManager.getUsername() == null || userManager.getUsername().trim().isEmpty()) {
            throw new ValidationException("Le nom d'utilisateur est obligatoire");
        }

        if (userManager.getPasswordHash() == null || userManager.getPasswordHash().trim().isEmpty()) {
            throw new ValidationException("Le mot de passe est obligatoire");
        }

        if (userManager.getRole() == null || userManager.getRole().trim().isEmpty()) {
            throw new ValidationException("Le rôle est obligatoire");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            
            UserManager existing = repository.findByUsername(userManager.getUsername());
            if (existing != null) {
                throw new ValidationException("Ce nom d'utilisateur est déjà utilisé");
            }

            String hashedPassword = PasswordUtil.hashPassword(userManager.getPasswordHash());
            userManager.setPasswordHash(hashedPassword);
            
            if (userManager.getActif() == null) {
                userManager.setActif(true);
            }
            
            if (userManager.getDateCreation() == null) {
                userManager.setDateCreation(new Date());
            }

            return repository.save(userManager);
        }
    }

    @Override
    public UserManager updateUser(UserManager userManager) throws Exception {
        if (userManager == null) {
            throw new ValidationException("L'utilisateur ne peut pas être null");
        }

        if (userManager.getIdUser() == null || userManager.getIdUser() <= 0) {
            throw new ValidationException("ID utilisateur invalide");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            
            UserManager existing = repository.findById(userManager.getIdUser());
            if (existing == null) {
                throw new Exception("Utilisateur introuvable");
            }

            return repository.update(userManager);
        }
    }

    @Override
    public void deleteUser(Integer idUser) throws Exception {
        if (idUser == null || idUser <= 0) {
            throw new ValidationException("ID utilisateur invalide");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            
            UserManager existing = repository.findById(idUser);
            if (existing == null) {
                throw new Exception("Utilisateur introuvable");
            }

            repository.delete(idUser);
        }
    }

    @Override
    public UserManager findById(Integer idUser) throws Exception {
        if (idUser == null || idUser <= 0) {
            throw new ValidationException("ID utilisateur invalide");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            UserManager user = repository.findById(idUser);
            
            if (user == null) {
                throw new Exception("Utilisateur introuvable");
            }
            
            return user;
        }
    }

    @Override
    public UserManager findByUsername(String username) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Le nom d'utilisateur ne peut pas être vide");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            UserManager user = repository.findByUsername(username);
            
            if (user == null) {
                throw new Exception("Utilisateur introuvable");
            }
            
            return user;
        }
    }

    @Override
    public List<UserManager> findAll() throws Exception {
        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            List<UserManager> users = repository.findAll();
            
            if (users == null || users.isEmpty()) {
                throw new Exception("Aucun utilisateur trouvé");
            }
            
            return users;
        }
    }

    @Override
    public UserManager authenticateUser(String username, String password) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Le nom d'utilisateur est obligatoire");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Le mot de passe est obligatoire");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            UserManager user = repository.findByUsername(username);
            
            if (user == null) {
                throw new Exception("Nom d'utilisateur ou mot de passe incorrect");
            }

            if (!user.getActif()) {
                throw new Exception("Ce compte est désactivé");
            }

            String hashedPassword = PasswordUtil.hashPassword(password);
            if (!hashedPassword.equals(user.getPasswordHash())) {
                throw new Exception("Nom d'utilisateur ou mot de passe incorrect");
            }

            return user;
        }
    }

    @Override
    public boolean activateUser(Integer idUser) throws Exception {
        if (idUser == null || idUser <= 0) {
            throw new ValidationException("ID utilisateur invalide");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            UserManager user = repository.findById(idUser);
            
            if (user == null) {
                throw new Exception("Utilisateur introuvable");
            }

            user.setActif(true);
            repository.update(user);
            return true;
        }
    }

    @Override
    public boolean deactivateUser(Integer idUser) throws Exception {
        if (idUser == null || idUser <= 0) {
            throw new ValidationException("ID utilisateur invalide");
        }

        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            UserManager user = repository.findById(idUser);
            
            if (user == null) {
                throw new Exception("Utilisateur introuvable");
            }

            user.setActif(false);
            repository.update(user);
            return true;
        }
    }

    @Override
    public long countActiveUsers() throws Exception {
        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            UserManagerRepositoryImpl repository = new UserManagerRepositoryImpl(conn);
            List<UserManager> allUsers = repository.findAll();
            
            return allUsers.stream()
                    .filter(u -> u.getActif() != null && u.getActif())
                    .count();
        }
    }
}
