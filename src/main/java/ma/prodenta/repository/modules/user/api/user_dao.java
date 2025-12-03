package ma.prodenta.repository.modules.user.api;
import  ma.prodenta.entities.En.Utilisateur;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import ma.prodenta.repository.common.CrudRepository;
public interface user_dao extends CrudRepository<Utilisateur, Integer> {
    Utilisateur getUser(String username,String password) throws FileNotFoundException, SQLException;
}