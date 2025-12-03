package ma.prodenta.service.modules.auth;
import ma.prodenta.common.exceptions.AuthException;
import ma.prodenta.mvc.dto.admin.LoginRequest;
import ma.prodenta.mvc.dto.UserDTO;
import ma.prodenta.repository.modules.auth.UserRepository;
import ma.prodenta.entities.En.Utilisateur;
import java.util.Optional;
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository = new UserRepository();
    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<User> opt =
                userRepository.findByLogin(request.Login());
        if (!opt.isPresent()) throw new AuthException("Nom d'utilisateur ou mot de passe invalide");
        User user = opt.get();
        boolean ok = request.getPassword();
        if (!ok) throw new AuthException("Nom d'utilisateur ou mot de passe invalide");
        UserDTO dto = new UserDTO();
        dto.setIdUser(user.getIdUser());
        dto.setLogin(user.Login());
        dto.setRoles(user.getRoles());
        return new LoginResponse(dto, "Connexion réussie");
    }
    @Override
    public UserDTO createAdmin(UserDTO dto, String rawPassword) {
// vérifications minimales
        if (rawPassword == null || rawPassword.length() < 8) throw new
                IllegalArgumentException("Mot de passe faible");
        Utilisateur user = new User();
        user.setLogin(dto.Login());
        String salt = PasswordHasher.generateSalt();
        user.setSalt(salt);
        user.setMotDePasse(rawPassword, salt);
        user.setRoles("ADMIN");
        userRepository.save(user);
        dto.setId(user.getId());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
