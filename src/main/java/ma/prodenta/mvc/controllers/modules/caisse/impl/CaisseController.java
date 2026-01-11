package ma.prodenta.mvc.controllers.modules.caisse.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.modules.Caisse.implementation.ChargesRepositoryImpl;
import ma.prodenta.service.modules.caisse.impl.ChargesServiceImpl;

import java.sql.Connection;
import java.util.List;

public class CaisseController {
    private ChargesServiceImpl chargesService;

    public CaisseController() {
        try {
            Connection conn = SessionFactory.getInstance().getConnection();
            ChargesRepositoryImpl chargesRepository = new ChargesRepositoryImpl(conn);
            this.chargesService = new ChargesServiceImpl(chargesRepository);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation du contrôleur Caisse: " + e.getMessage());
        }
    }

    public List<Charges> getAllCharges() throws Exception {
        return chargesService.findAll();
    }

    public Charges getChargesById(Integer id) throws Exception {
        return chargesService.findById(id);
    }

    public Charges createCharges(Charges charges) throws Exception {
        return chargesService.create(charges);
    }

    public Charges updateCharges(Charges charges) throws Exception {
        return chargesService.update(charges);
    }

    public boolean deleteCharges(Integer id) throws Exception {
        return chargesService.delete(id);
    }

    public List<Charges> getChargesByCabinet(Integer idCabinet) throws Exception {
        return chargesService.findByCabinet(idCabinet);
    }
}
