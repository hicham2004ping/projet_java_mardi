package ma.prodenta.mvc.controllers.modules.antecedent.api;

import ma.prodenta.entities.En.Antecedent;

import java.util.List;

public interface Antecedent_Controlleur_Api {
    public List<Antecedent> find_all() throws Exception;
    public Antecedent find_by_id(int id);
    public void ajouter(Antecedent e);
    public void supprimer(Antecedent e);
    public void update(Antecedent e);
}
