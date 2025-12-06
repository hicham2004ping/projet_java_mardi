package ma.prodenta.service.modules.Revenus.impl;

import ma.prodenta.entities.En.Revenus;
import ma.prodenta.repository.modules.statistiques.api.RevenusDao;
import ma.prodenta.service.modules.Revenus.api.RevenusService;

import java.util.List;

public class RevenusServiceImpl implements RevenusService {

    private final RevenusDao revenusDao;

    public RevenusServiceImpl(RevenusDao revenusDao) {
        this.revenusDao = revenusDao;
    }

    @Override
    public Revenus getRevenuById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("L'id du revenu ne doit pas être null");
        }
        return revenusDao.findById(id);
    }

    @Override
    public boolean createRevenu(Revenus revenu) throws Exception {
        if (revenu == null) {
            throw new IllegalArgumentException("L'objet revenu ne doit pas être null");
        }

        // Exemple de validation métier supplémentaire possible :
        // if (revenu.getMontant() <= 0) throw new IllegalArgumentException("Montant invalide");

        return revenusDao.create(revenu);
    }

    @Override
    public List<Revenus> getAllRevenus() throws Exception {
        return revenusDao.findAll();
    }

    @Override
    public void updateRevenu(Revenus revenu) throws Exception {
        if (revenu == null || revenu.getIdRev() == null) {
            throw new IllegalArgumentException("Le revenu ou son identifiant ne doivent pas être null");
        }
        revenusDao.update(revenu);
    }

    @Override
    public boolean deleteRevenuById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("L'id du revenu ne doit pas être null");
        }
        return revenusDao.deleteById(id);
    }
}

