package ma.prodenta.mvc.dto;
public class LoginResponse {
    private UserDTO user;
    private String message;
    public LoginResponse(UserDTO user, String message) {
        this.user = user; this.message = message;
    }
    public UserDTO getUser() { return user; }
    public String getMessage() { return message; }
}