// repository/modules/UserManager/api/UserManagerRepository.java
package ma.prodenta.repository.modules.userManager.api;
import ma.prodenta.entities.En.UserManager;
import java.util.List;

public interface UserManagerRepository {
    UserManager findById(Integer id) throws Exception;
    UserManager findByUsername(String username) throws Exception;
    List<UserManager> findAll() throws Exception;
    UserManager save(UserManager user) throws Exception;
    UserManager update(UserManager user) throws Exception;
    void delete(Integer id) throws Exception;
}
