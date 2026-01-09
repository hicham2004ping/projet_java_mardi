package ma.prodenta.service.modules.agenda.impl;

import ma.prodenta.common.exceptions.ArgumentException;
import ma.prodenta.common.exceptions.ErreurLectureException;
import ma.prodenta.common.exceptions.ErreurSuppressionException;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Agenda;
import ma.prodenta.repository.modules.agenda.implementation.AgendaRepositoryImpl;
import ma.prodenta.service.modules.agenda.api.AgendaService;
import java.util.Date;
import java.util.List;

public class AgendaServiceImpl implements AgendaService {

    @Override
    public Agenda findById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new ArgumentException("l'id de l'agenda est invalide");
        }
        AgendaRepositoryImpl agendaRepo = Application_contexte.getAgendaRepository();
        Agenda agenda = agendaRepo.findById(id);
        if (agenda == null) {
            throw new ErreurLectureException("aucun agenda n'existe avec cette id");
        }
        return agenda;
    }

    @Override
    public List<Agenda> findAll() throws Exception {
        AgendaRepositoryImpl agendaRepo = Application_contexte.getAgendaRepository();
        List<Agenda> agendas = agendaRepo.findAll();
        if (agendas == null || agendas.isEmpty()) {
            throw new ErreurLectureException("aucun agenda n'existe dans la base");
        }
        return agendas;
    }

    @Override
    public List<Agenda> findByDate(Date date) throws Exception {
        if (date == null) {
            throw new ArgumentException("la date fournie est invalide");
        }
        AgendaRepositoryImpl agendaRepo = Application_contexte.getAgendaRepository();
        List<Agenda> agendas = agendaRepo.findByDate(date);
        if (agendas == null || agendas.isEmpty()) {
            throw new ErreurLectureException("aucun agenda n'existe pour cette date");
        }
        return agendas;
    }

    @Override
    public Agenda save(Agenda agenda) throws Exception {
        if (agenda == null) {
            throw new ArgumentException("l'agenda fourni est null");
        }
        if (agenda.getIdMedecin() == null || agenda.getIdMedecin() <= 0) {
            throw new ArgumentException("l'id du medecin est invalide");
        }
        if (agenda.getIdPatient() == null || agenda.getIdPatient() <= 0) {
            throw new ArgumentException("l'id du patient est invalide");
        }
        if (agenda.getDateDebut() == null || agenda.getDateFin() == null) {
            throw new ArgumentException("les dates de l'agenda sont invalides");
        }
        AgendaRepositoryImpl agendaRepo = Application_contexte.getAgendaRepository();
        return agendaRepo.save(agenda);
    }

    @Override
    public Agenda update(Agenda agenda) throws Exception {
        if (agenda == null) {
            throw new ArgumentException("l'agenda fourni est null");
        }
        if (agenda.getIdAgenda() == null || agenda.getIdAgenda() <= 0) {
            throw new ArgumentException("l'id de l'agenda est invalide");
        }
        if (agenda.getIdMedecin() == null || agenda.getIdMedecin() <= 0) {
            throw new ArgumentException("l'id du medecin est invalide");
        }
        if (agenda.getIdPatient() == null || agenda.getIdPatient() <= 0) {
            throw new ArgumentException("l'id du patient est invalide");
        }
        AgendaRepositoryImpl agendaRepo = Application_contexte.getAgendaRepository();
        return agendaRepo.update(agenda);
    }

    @Override
    public void delete(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new ArgumentException("l'id de l'agenda est invalide");
        }
        AgendaRepositoryImpl agendaRepo = Application_contexte.getAgendaRepository();
        Agenda existingAgenda = agendaRepo.findById(id);
        if (existingAgenda == null) {
            throw new ErreurLectureException("impossible de supprimer, aucun agenda avec cette id");
        }
        agendaRepo.delete(id);
    }
}
