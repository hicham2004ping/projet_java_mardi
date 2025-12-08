package ma.prodenta.service.modules.antecedent.api;
import ma.prodenta.entities.En.Antecedent;
import java.util.List;
import java.util.Optional;

public interface Antecedent_Service_api
{
    boolean create(Antecedent antecedent) throws Exception;

    void update(Antecedent antecedent) throws Exception;

    boolean delete(Antecedent antecedent) throws Exception;

    boolean deleteById(Integer id) throws Exception;

    Antecedent findById(Integer id) throws Exception;

    Optional<Antecedent> findByNom(String nom) throws Exception;

    List<Antecedent> findAll() throws Exception;

    List<String> findAllNames() throws Exception;

    int idParNom(String nom) throws Exception;
}
