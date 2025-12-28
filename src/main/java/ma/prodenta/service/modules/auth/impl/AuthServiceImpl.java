package ma.prodenta.service.modules.auth.impl;

import ma.prodenta.common.exceptions.AuthException;
import ma.prodenta.common.exceptions.ValidationException;
import ma.prodenta.common.util.PasswordUtil;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.auth.implementation.AuthDaoImpl;
import ma.prodenta.service.modules.auth.api.AuthService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AuthServiceImpl implements AuthService {
    
    private final AuthDaoImpl authRepository;

    public AuthServiceImpl() {
        this.authRepository = new AuthDaoImpl();
    }

    @Override
    public Utilisateur authenticate(String login, String motdepasse) throws Exception {
        if (login == null || login.trim().isEmpty()) {
            throw new ValidationException("Le login est obligatoire");
        }
        
        if (motdepasse == null || motdepasse.trim().isEmpty()) {
            throw new ValidationException("Le mot de passe est obligatoire");
        }

        String hashedPassword = PasswordUtil.hashPassword(motdepasse);
        
        Optional<Utilisateur> userOpt = authRepository.login(login, hashedPassword);
        
        if (!userOpt.isPresent()) {
            throw new AuthException("Login ou mot de passe incorrect");
        }

        Utilisateur user = userOpt.get();
        user.setLastLoginDate(LocalDateTime.now());
        authRepository.update(user);

        return user;
    }

    @Override
    public Utilisateur register(Utilisateur utilisateur) throws Exception {
        if (utilisateur == null) {
            throw new ValidationException("L'utilisateur ne peut pas être null");
        }

        if (utilisateur.getLogin() == null || utilisateur.getLogin().trim().isEmpty()) {
            throw new ValidationException("Le login est obligatoire");
        }

        if (utilisateur.getMotdepasse() == null || utilisateur.getMotdepasse().trim().isEmpty()) {
            throw new ValidationException("Le mot de passe est obligatoire");
        }

        if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty()) {
            throw new ValidationException("L'email est obligatoire");
        }

        if (authRepository.existsByLogin(utilisateur.getLogin())) {
            throw new ValidationException("Ce login est déjà utilisé");
        }

        if (authRepository.existsByEmail(utilisateur.getEmail())) {
            throw new ValidationException("Cet email est déjà utilisé");
        }

        String hashedPassword = PasswordUtil.hashPassword(utilisateur.getMotdepasse());
        utilisateur.setMotdepasse(hashedPassword);

        boolean created = authRepository.create(utilisateur);
        
        if (!created) {
            throw new Exception("Erreur lors de la création de l'utilisateur");
        }

        return utilisateur;
    }

    @Override
    public Utilisateur findByLogin(String login) throws Exception {
        if (login == null || login.trim().isEmpty()) {
            throw new ValidationException("Le login ne peut pas être vide");
        }

        Optional<Utilisateur> userOpt = authRepository.findByLogin(login);
        
        if (!userOpt.isPresent()) {
            throw new Exception("Aucun utilisateur trouvé avec ce login");
        }

        return userOpt.get();
    }

    @Override
    public Utilisateur findByEmail(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("L'email ne peut pas être vide");
        }

        Optional<Utilisateur> userOpt = authRepository.findByEmail(email);
        
        if (!userOpt.isPresent()) {
            throw new Exception("Aucun utilisateur trouvé avec cet email");
        }

        return userOpt.get();
    }

    @Override
    public boolean existsByLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            return false;
        }
        return authRepository.existsByLogin(login);
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return authRepository.existsByEmail(email);
    }

    @Override
    public long countUsers() {
        return authRepository.count();
    }

    @Override
    public List<Utilisateur> getAllUsers() throws Exception {
        List<Utilisateur> users = authRepository.findAll();
        
        if (users == null || users.isEmpty()) {
            throw new Exception("Aucun utilisateur trouvé dans la base de données");
        }

        return users;
    }

    @Override
    public void updateUser(Utilisateur utilisateur) throws Exception {
        if (utilisateur == null) {
            throw new ValidationException("L'utilisateur ne peut pas être null");
        }

        if (utilisateur.getIdUser() <= 0) {
            throw new ValidationException("ID utilisateur invalide");
        }

        Utilisateur existing = authRepository.findById(utilisateur.getIdUser());
        if (existing == null) {
            throw new Exception("Utilisateur introuvable");
        }

        authRepository.update(utilisateur);
    }

    @Override
    public boolean deleteUser(Integer idUser) throws Exception {
        if (idUser == null || idUser <= 0) {
            throw new ValidationException("ID utilisateur invalide");
        }

        Utilisateur existing = authRepository.findById(idUser);
        if (existing == null) {
            throw new Exception("Utilisateur introuvable");
        }

        return authRepository.deleteById(idUser);
    }
}
