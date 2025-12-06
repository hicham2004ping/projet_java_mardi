package ma.prodenta.service.modules.charge.impl;

import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.modules.statistiques.api.ChargesDao;
import ma.prodenta.service.modules.charge.api.ChargesService;

import java.util.List;

public class ChargesServiceImpl implements ChargesService {

    private final ChargesDao chargesDao;

    // Injection par constructeur (tu peux aussi utiliser un setter ou un framework DI)
    public ChargesServiceImpl(ChargesDao chargesDao) {
        this.chargesDao = chargesDao;
    }

    @Override
    public Charges getChargeById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("L'id de la charge ne doit pas être null");
        }
        return chargesDao.findById(id);
    }

    @Override
    public boolean createCharge(Charges charge) throws Exception {
        if (charge == null) {
            throw new IllegalArgumentException("L'objet charge ne doit pas être null");
        }
        // Ici tu peux rajouter des règles métier (montant > 0, titre non vide, etc.)
        return chargesDao.create(charge);
    }

    @Override
    public List<Charges> getAllCharges() throws Exception {
        return chargesDao.findAll();
    }

    @Override
    public void updateCharge(Charges charge) throws Exception {
        if (charge == null || charge.getIdCharge() == null) {
            throw new IllegalArgumentException("La charge ou son id ne doivent pas être null");
        }
        chargesDao.update(charge);
    }

    @Override
    public boolean deleteChargeById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("L'id de la charge ne doit pas être null");
        }
        return chargesDao.deleteById(id);
    }
}
