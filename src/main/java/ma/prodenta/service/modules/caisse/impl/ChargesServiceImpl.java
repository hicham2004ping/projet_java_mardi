package ma.prodenta.service.modules.caisse.impl;
import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.modules.caisse.api.ChargesRepository;
import ma.prodenta.service.modules.caisse.api.ChargesService;

import java.util.List;

public class ChargesServiceImpl implements ChargesService {

    private final ChargesRepository repo;

    public ChargesServiceImpl(ChargesRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Charges> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public Charges findById(Integer id) throws Exception {
        return repo.findById(id);
    }

    @Override
    public Charges create(Charges charge) throws Exception {
        // Ici tu peux ajouter des règles métier (vérif montant > 0, etc.)
        return repo.save(charge);
    }

    @Override
    public Charges update(Charges charge) throws Exception {
        return repo.update(charge);
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        repo.delete(id);
        return true;
    }

    @Override
    public List<Charges> findByCabinet(Integer idCabinet) throws Exception {
        // Si tu n'as pas de méthode dédiée dans le repo,
        // on filtre en mémoire à partir de findAll()
        return repo.findAll().stream()
                .filter(c -> c.getIdCabinet() != null && c.getIdCabinet().equals(idCabinet))
                .toList();
    }
}
