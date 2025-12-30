package ma.prodenta.mvc.controllers.modules.antecedent.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.mvc.controllers.modules.antecedent.api.Antecedent_Controlleur_Api;
import ma.prodenta.service.modules.antecedent.api.Antecedent_Service_api;
import ma.prodenta.service.modules.antecedent.impl.Antecedent_Service_ServiceImpl;

import java.util.List;

public class Antecedent_Controlleur_Impl implements Antecedent_Controlleur_Api {
    private Antecedent_Service_ServiceImpl antecedent_serice;
    public Antecedent_Controlleur_Impl() {
        this.antecedent_serice = Application_contexte.getAntecedent_Service();
    }

    @Override
    public List<Antecedent> find_all() throws Exception {
        try {
            return antecedent_serice.findAll();
        } catch (Exception e) {
            throw new Exception("impossible de charger la liste des antecedents");
        }
    }

    @Override
    public Antecedent find_by_id(int id) {
        return null;
    }

    @Override
    public void ajouter(Antecedent e) {

    }

    @Override
    public void supprimer(Antecedent e) {

    }

    @Override
    public void update(Antecedent e) {

    }
}
