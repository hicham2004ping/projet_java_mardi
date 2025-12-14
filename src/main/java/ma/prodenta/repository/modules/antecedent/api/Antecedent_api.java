package ma.prodenta.repository.modules.antecedent.api;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.repository.common.CrudRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public interface Antecedent_api extends CrudRepository<Antecedent,Integer> {
    boolean create(Patient patient) throws SQLException;

    public List<String> find_all_names() throws SQLException, ClassNotFoundException, IOException ;
    public int id_par_nom(String nom) throws SQLException, ClassNotFoundException ,IOException;
    public NiveauRisque map_to_enum(int id ) throws Exception,SQLException;
    public int map_to_int(NiveauRisque n) throws Exception, SQLException;
}
