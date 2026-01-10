package ma.prodenta.mvc.controllers.modules.rdv;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.RDV;
import ma.prodenta.mvc.dto.rdv.RDVDTO;
import ma.prodenta.repository.modules.RendezVous.api.RDVDAO;
import ma.prodenta.service.modules.rendezvous.api.RDVI;
import ma.prodenta.service.modules.rendezvous.baseImplementation.RDVimple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RDVController {

    private RDVI rdvService;
    private RDVDAO rdvRepository;

    public RDVController() {
        this.rdvService = new RDVimple();
        this.rdvRepository = Application_contexte.getRDVRepository();
    }
    public boolean ajouterRDV(RDVDTO dto) throws Exception {
        RDV rdv = dto.toEntity();
        return rdvService.create(rdv);
    }

    public boolean modifierRDV(RDVDTO dto) throws Exception {
        RDV rdv = dto.toEntity();
        return rdvService.update(rdv);
    }

    public void supprimerRDV(Integer id) throws Exception {
        RDV rdv = rdvService.find(id);
        if (rdv != null) {
            rdvService.delete(rdv);
        }
    }

    public List<RDVDTO> afficherTous() {
        List<RDVDTO> dtos = new ArrayList<>();
        try {
            List<RDV> rdvs = rdvRepository.findAll();
            for (RDV rdv : rdvs) {
                dtos.add(RDVDTO.rdvToDTO(rdv));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des RDV: " + e.getMessage());
        }
        return dtos;
    }

    public List<RDVDTO> afficherParDossier(Integer idDossier) {
        List<RDVDTO> dtos = new ArrayList<>();
        try {
            List<RDV> rdvs = rdvRepository.findByDossier(idDossier);
            for (RDV rdv : rdvs) {
                dtos.add(RDVDTO.rdvToDTO(rdv));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des RDV par dossier: " + e.getMessage());
        }
        return dtos;
    }

    public List<RDVDTO> afficherParDate(Date date) {
        List<RDVDTO> dtos = new ArrayList<>();
        try {
            List<RDV> rdvs = rdvService.TrouveCrenau(date);
            for (RDV rdv : rdvs) {
                dtos.add(RDVDTO.rdvToDTO(rdv));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des RDV par date: " + e.getMessage());
        }
        return dtos;
    }

    public RDVDTO afficherParId(Integer id) throws Exception {
        RDV rdv = rdvService.find(id);
        return RDVDTO.rdvToDTO(rdv);
    }

    public List<Integer> trouverCrenauxLibres(Date date) throws Exception {
        return rdvService.TrouveCrenauLibre(date);
    }

    public boolean estCreneauDisponible(Date date, int heure) throws Exception {
        List<Integer> creneauxLibres = trouverCrenauxLibres(date);
        return creneauxLibres.contains(heure);
    }
}

