package ma.prodenta.service.modules.situationfinanciere.impl;

import ma.prodenta.entities.En.SituationFinanciere;
import ma.prodenta.repository.modules.SituationFinanciere.api.SituationFinancierDao;
import ma.prodenta.service.modules.situationfinanciere.api.SituationFinanciereService;

import java.util.List;

public class SituationFinanciereServiceImpl implements SituationFinanciereService {

    private final SituationFinancierDao dao;

    public SituationFinanciereServiceImpl(SituationFinancierDao dao) {
        this.dao = dao;
    }

    @Override
    public List<SituationFinanciere> findAll() throws Exception {
        return dao.findAll();
    }

    @Override
    public SituationFinanciere findById(Integer idSF) throws Exception {
        if (idSF == null) return null;
        return dao.findById(idSF.longValue()); // le DAO utilise Long
    }

    @Override
    public boolean create(SituationFinanciere sf) throws Exception {
        // petite logique métier : calcul du crédit si null
        if (sf.getCredit() == null
                && sf.getTotalActes() != null
                && sf.getTotalPaye() != null) {
            sf.setCredit(sf.getTotalActes() - sf.getTotalPaye());
        }
        return dao.create(sf);
    }

    @Override
    public void update(SituationFinanciere sf) throws Exception {
        if (sf.getCredit() == null
                && sf.getTotalActes() != null
                && sf.getTotalPaye() != null) {
            sf.setCredit(sf.getTotalActes() - sf.getTotalPaye());
        }
        dao.update(sf);
    }

    @Override
    public boolean delete(SituationFinanciere sf) throws Exception {
        return dao.delete(sf);
    }

    @Override
    public boolean deleteById(Integer idSF) throws Exception {
        if (idSF == null) return false;
        return dao.deleteById(idSF.longValue());
    }

    @Override
    public List<SituationFinanciere> findByPatient(Integer idPatient) throws Exception {
        if (idPatient == null) return List.of();
        // pas de méthode dédiée dans le DAO → on filtre en mémoire
        return dao.findAll().stream()
                .filter(sf -> sf.getIdPatient() != null && sf.getIdPatient().equals(idPatient))
                .toList();
    }
}
