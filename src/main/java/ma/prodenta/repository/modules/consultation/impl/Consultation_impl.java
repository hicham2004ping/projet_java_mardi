package ma.prodenta.repository.modules.consultation.impl;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.repository.modules.consultation.api.Consultation_api;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Consultation_impl implements Consultation_api {

    @Override
    public int total_consultations() {
        return 0;
    }

    @Override
    public List<Consultation> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Consultation findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public boolean create(Consultation objet) throws SQLException, IOException {
        return false;
    }

    @Override
    public void update(Consultation objet) throws SQLException, IOException, Exception {

    }

    @Override
    public boolean delete(Consultation objet) throws SQLException, Exception {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException, Exception {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}
