package ma.prodenta.mvc.controllers.modules.auth;
import ma.prodenta.mvc.dto.admin.LoginRequest;
import ma.prodenta.service.modules.auth.AuthService;
import ma.prodenta.service.modules.auth.AuthServiceImpl;
public class AuthController {
    private final AuthService authService = new AuthServiceImpl();
    public LoginResponse login(LoginRequest req) {
        return authService.login(req);
    }
    public void createAdmin(String username, String password) {
        ma.prodenta.mvc.dto.UserDTO dto = new ma.prodenta.mvc.dto.UserDTO();
        dto.setUsername(username);
        authService.createAdmin(dto, password);
    }
}