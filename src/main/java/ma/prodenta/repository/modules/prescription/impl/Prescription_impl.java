package ma.prodenta.repository.modules.prescription.impl;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.repository.modules.prescription.api.Prescription_api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Prescription_impl implements Prescription_api {
    @Override
    public int total_prescriptions(Ordonnance ordonance) {
        return 0;
    }

    @Override
    public List<Prescription> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Prescription findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public boolean create(Prescription objet) throws SQLException, IOException {
        return false;
    }

    @Override
    public void update(Prescription objet) throws SQLException, IOException, Exception {

    }

    @Override
    public boolean delete(Prescription objet) throws SQLException, Exception {
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
