package ma.prodenta.repository.modules.admin.api;
import ma.prodenta.entities.En.Admin;
import ma.prodenta.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;
public interface AdminDao extends CrudRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username) throws Exception;
    Optional<Admin> findByEmail(String email) throws Exception;
    boolean existsById(int  id);
    long count();
    List<Admin> findPage(int limit, int offset);
    // Auth
    Optional<Admin> login(String username, String passwordHash) throws Exception;
}
