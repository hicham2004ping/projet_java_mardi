package ma.prodenta.repository.common;
import ma.prodenta.entities.En.Admin;
import java.util.Optional;

public interface AdminRepository {

    Optional<Admin> findByUsername(String username);

    void updateLastLogin(Long adminId);

}
