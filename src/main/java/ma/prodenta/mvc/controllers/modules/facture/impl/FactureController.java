package ma.prodenta.mvc.controllers.modules.facture.impl;

import ma.prodenta.entities.En.Facture;
import ma.prodenta.repository.modules.Facture.impl.FactureDaoimpl;
import ma.prodenta.service.modules.facture.impl.FactureServiceImpl;

import java.util.List;

/**
 * FactureController - Controller for invoice management operations
 */
public class FactureController {
    private FactureServiceImpl factureService;

    public FactureController() {
        try {
            this.factureService = new FactureServiceImpl(new FactureDaoimpl());
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation du contrôleur Facture: " + e.getMessage());
        }
    }

    public List<Facture> getAllFactures() throws Exception {
        return factureService.findAll();
    }

    public Facture getFactureById(Integer id) throws Exception {
        return factureService.findById(id);
    }

    public boolean createFacture(Facture facture) throws Exception {
        return factureService.create(facture);
    }

    public void updateFacture(Facture facture) throws Exception {
        factureService.update(facture);
    }

    public boolean deleteFacture(Integer id) throws Exception {
        return factureService.deleteById(id);
    }

    public List<Facture> getFacturesBySituationFinanciere(Integer idSF) throws Exception {
        return factureService.findBySituationFinanciere(idSF);
    }
}
