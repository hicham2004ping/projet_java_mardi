package ma.prodenta.service.modules.antecedent.impl;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.service.modules.antecedent.api.Antecedent_Service_api;
import java.util.List;
import java.util.Optional;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.entities.En.Antecedent;

public class Antecedent_Service_ServiceImpl implements Antecedent_Service_api {


    @Override
    public boolean create(Antecedent antecedent) throws Exception {

    }

    @Override
    public void update(Antecedent antecedent) throws Exception {

    }

    @Override
    public boolean delete(Antecedent antecedent) throws Exception {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) throws Exception {
        return false;
    }

    @Override
    public Antecedent findById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<Antecedent> findAll() throws Exception {
        return List.of();
    }

    @Override
    public List<String> findAllNames() throws Exception {
        return List.of();
    }

    @Override
    public int idParNom(String nom) throws Exception {
        return 0;
    }
}