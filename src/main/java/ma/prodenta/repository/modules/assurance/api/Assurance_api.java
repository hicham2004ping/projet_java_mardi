package ma.prodenta.repository.modules.assurance.api;
import ma.prodenta.entities.En.Assurance_c;
import ma.prodenta.repository.common.CrudRepository;
import ma.prodenta.entities.Enum.Assurance;
import java.sql.SQLException;

public interface Assurance_api extends CrudRepository<Assurance_c,Integer> {
    public int map_to_int(Assurance_c a) throws Exception;
    public Assurance map_to_enum(int  id) throws Exception;
    public Assurance_c find_by_name(String nom) throws Exception;
    public int get_last_id() throws SQLException;
    public int id_assurance(String libelle) throws SQLException;
}
