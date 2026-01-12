package ma.prodenta.mvc.controllers.modules.agenda;

import ma.prodenta.entities.En.Agenda;
import ma.prodenta.mvc.dto.agenda.AgendaDTO;
import ma.prodenta.service.modules.agenda.api.AgendaService;
import ma.prodenta.service.modules.agenda.baseImplementation.AgendaImple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendaController {

    private AgendaService service;

    public AgendaController() {
        this.service = new AgendaImple();
    }

    public boolean ajouterAgenda(AgendaDTO dto) throws Exception {
        service.save(dto.toEntity());
        return true;
    }

    public boolean modifierAgenda(AgendaDTO dto) throws Exception {
        service.update(dto.toEntity());
        return true;
    }

    public void supprimerAgenda(Integer id) throws Exception {
        service.delete(id);
    }

    public AgendaDTO afficherParId(Integer id) throws Exception {
        Agenda agenda = service.findById(id);
        return AgendaDTO.fromEntity(agenda);
    }

    public List<AgendaDTO> afficherTous() {
        List<AgendaDTO> dtos = new ArrayList<>();
        try {
            List<Agenda> agendas = service.findAll();
            for (Agenda agenda : agendas) {
                dtos.add(AgendaDTO.fromEntity(agenda));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dtos;
    }

    public List<AgendaDTO> afficherParDate(Date date) {
        List<AgendaDTO> dtos = new ArrayList<>();
        try {
            List<Agenda> agendas = service.findByDate(date);
            for (Agenda agenda : agendas) {
                dtos.add(AgendaDTO.fromEntity(agenda));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dtos;
    }
}
