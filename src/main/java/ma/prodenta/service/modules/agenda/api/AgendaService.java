package ma.prodenta.service.modules.agenda.api;

import ma.prodenta.entities.En.Agenda;
import java.util.Date;
import java.util.List;

public interface AgendaService {
    Agenda findById(Integer id) throws Exception;
    List<Agenda> findAll() throws Exception;
    List<Agenda> findByDate(Date date) throws Exception;
    Agenda save(Agenda agenda) throws Exception;
    Agenda update(Agenda agenda) throws Exception;
    void delete(Integer id) throws Exception;
}
