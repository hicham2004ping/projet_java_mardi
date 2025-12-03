package ma.prodenta.mvc.dto;
public class UserDTO {
    private Long id;
    private String username;
    private String roles; // CSV ou simple string
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
}