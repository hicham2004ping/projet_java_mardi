package ma.prodenta.repository.modules.sexe.api;
import ma.prodenta.entities.En.Sexe_c;
import ma.prodenta.repository.common.CrudRepository;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Sexe_api  extends CrudRepository<Sexe_c, Integer> {
    public Sexe_c findBylibelle(String libelle);
    public Sexe_c map_to_sexe(ResultSet rs) throws SQLException;
}
