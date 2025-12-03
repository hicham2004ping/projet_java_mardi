package ma.prodenta.entities.En;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    private int id;
    private String username;
    private String nom;
    private String password_hash;
    private String email;
    private String role;
    private LocalDateTime lastLoginDate;
    private Integer idRole;

    public void setPasswordHash(String passwordHash) {
        
    }

    public void setLastLoginAt(LocalDateTime localDateTime) {
    }

    public String getPasswordHash() {
        return "";
    }

    public LocalDateTime getLastLoginAt() {
        return null;
    }
}
