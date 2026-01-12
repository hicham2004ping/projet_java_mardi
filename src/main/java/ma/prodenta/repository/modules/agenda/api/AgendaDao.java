package ma.prodenta.repository.modules.agenda.api;

import ma.prodenta.entities.En.Agenda;
import ma.prodenta.repository.common.CrudRepository;

import java.util.Date;
import java.util.List;

public interface AgendaDao extends CrudRepository<Agenda, Integer> {
    List<Agenda> findByDate(Date date) throws Exception;
    List<Agenda> findByMedecin(Integer idMedecin) throws Exception;
}
