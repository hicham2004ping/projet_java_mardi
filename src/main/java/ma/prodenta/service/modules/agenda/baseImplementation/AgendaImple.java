package ma.prodenta.service.modules.agenda.baseImplementation;

import ma.prodenta.entities.En.Agenda;
import ma.prodenta.repository.modules.agenda.api.AgendaDao;
import ma.prodenta.repository.modules.agenda.baseImplementation.AgendaDaoImpl;
import ma.prodenta.service.modules.agenda.api.AgendaService;

import java.util.Date;
import java.util.List;

public class AgendaImple implements AgendaService {

    private AgendaDao dao;

    public AgendaImple() {
        this.dao = new AgendaDaoImpl();
    }

    @Override
    public Agenda findById(Integer id) throws Exception {
        return dao.findById(id);
    }

    @Override
    public List<Agenda> findAll() throws Exception {
        return dao.findAll();
    }

    @Override
    public List<Agenda> findByDate(Date date) throws Exception {
        return dao.findByDate(date);
    }

    @Override
    public Agenda save(Agenda agenda) throws Exception {
        if (dao.create(agenda)) {
            return agenda;
        }
        throw new Exception("Erreur lors de la création de l'agenda");
    }

    @Override
    public Agenda update(Agenda agenda) throws Exception {
        dao.update(agenda);
        return agenda;
    }

    @Override
    public void delete(Integer id) throws Exception {
        dao.deleteById(id);
    }
}
