package ma.prodenta.service.modules.auth;

import ma.prodenta.mvc.dto.LoginRequest;
import ma.prodenta.mvc.dto.LoginResponse;
import ma.prodenta.mvc.dto.UserDTO;
public interface AuthService {
    LoginResponse login(LoginRequest request);
    UserDTO createAdmin(UserDTO dto, String rawPassword);
}
