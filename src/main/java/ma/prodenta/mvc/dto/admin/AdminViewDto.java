package ma.prodenta.mvc.dto.admin;

import java.time.LocalDateTime;

/**
 if admin connected
 */
public class AdminViewDto {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime lastLoginAt;

    public AdminViewDto() {
        //khawya had sa3a
    }

    public AdminViewDto(Long id, String username, String fullName, String email, String role, LocalDateTime lastLoginAt) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.lastLoginAt = lastLoginAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
