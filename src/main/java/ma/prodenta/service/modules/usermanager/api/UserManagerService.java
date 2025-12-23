package ma.prodenta.service.modules.usermanager.api;

import ma.prodenta.entities.En.UserManager;

import java.util.List;

public interface UserManagerService {
    UserManager createUser(UserManager userManager) throws Exception;
    UserManager updateUser(UserManager userManager) throws Exception;
    void deleteUser(Integer idUser) throws Exception;
    UserManager findById(Integer idUser) throws Exception;
    UserManager findByUsername(String username) throws Exception;
    List<UserManager> findAll() throws Exception;
    UserManager authenticateUser(String username, String password) throws Exception;
    boolean activateUser(Integer idUser) throws Exception;
    boolean deactivateUser(Integer idUser) throws Exception;
    long countActiveUsers() throws Exception;
}
