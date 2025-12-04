package ma.prodenta.repository.modules.medcin.medcin_impl;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Medecin;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.repository.modules.medcin.api.Medcin_api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Medcin_impl implements Medcin_api {
    @Override
    public int total_dossier_mediceaux(Medecin medecin) {
        return 0;
    }

    @Override
    public List<Prescription> all_prescriptions(Medecin medecin) {
        return List.of();
    }

    @Override
    public List<Medecin> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Medecin findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public boolean create(Medecin objet) throws SQLException, IOException {
        return false;
    }

    @Override
    public void update(Medecin objet) throws SQLException, IOException, Exception {

    }

    @Override
    public boolean delete(Medecin objet) throws SQLException, Exception {
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
