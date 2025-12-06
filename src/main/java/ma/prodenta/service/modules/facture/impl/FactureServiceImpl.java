package ma.prodenta.service.modules.facture.impl;

import ma.prodenta.entities.En.Facture;
import ma.prodenta.repository.modules.Facture.api.FactureDao;
import ma.prodenta.service.modules.facture.api.FactureService;

import java.util.List;

public class FactureServiceImpl implements FactureService {

    private final FactureDao dao;

    public FactureServiceImpl(FactureDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Facture> findAll() throws Exception {
        return dao.findAll();
    }

    @Override
    public Facture findById(Integer idFact) throws Exception {
        return dao.findById(idFact);
    }

    @Override
    public boolean create(Facture facture) throws Exception {
        // petite logique métier : calcul du reste si null
        if (facture.getReste() == null && facture.getTotal() != null && facture.getTotalpaye() != null) {
            facture.setReste(facture.getTotal() - facture.getTotalpaye());
        }
        return dao.create(facture);
    }

    @Override
    public void update(Facture facture) throws Exception {
        if (facture.getReste() == null && facture.getTotal() != null && facture.getTotalpaye() != null) {
            facture.setReste(facture.getTotal() - facture.getTotalpaye());
        }
        dao.update(facture);
    }

    @Override
    public boolean delete(Facture facture) throws Exception {
        return dao.delete(facture);
    }

    @Override
    public boolean deleteById(Integer idFact) throws Exception {
        return dao.deleteById(idFact);
    }

    @Override
    public List<Facture> findBySituationFinanciere(Integer idSF) throws Exception {
        // pas de méthode dédiée dans le DAO, on filtre à partir de findAll()
        return dao.findAll().stream()
                .filter(f -> f.getIdSF() != null && f.getIdSF().equals(idSF))
                .toList();
    }
}
