package ma.prodenta.mvc.controllers.modules.ordonnance.impl;

import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.service.modules.ordonnance.impl.OrdonnanceImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * OrdonnanceController - Controller for prescription management operations
 */
public class OrdonnanceController {
    private OrdonnanceImpl ordonnanceService;

    public OrdonnanceController() {
        try {
            this.ordonnanceService = new OrdonnanceImpl();
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation du contrôleur Ordonnance: " + e.getMessage());
        }
    }

    public List<Ordonnance> getAllOrdonnances() {
        return ordonnanceService.findAll();
    }

    public Ordonnance getOrdonnanceById(Long id) {
        return ordonnanceService.findById(id);
    }

    public boolean createOrdonnance(Ordonnance ordonnance) throws Exception {
        return ordonnanceService.create(ordonnance);
    }

    public boolean deleteOrdonnance(Ordonnance ordonnance) throws Exception {
        return ordonnanceService.delete(ordonnance);
    }

    public List<Ordonnance> getOrdonnancesByPatient(int idPatient) {
        return ordonnanceService.findByPatient(idPatient);
    }

    public List<Ordonnance> getOrdonnancesByDate(LocalDate date) {
        return ordonnanceService.findByDate(date);
    }
}
