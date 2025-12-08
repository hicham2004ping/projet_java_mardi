package ma.prodenta.service.modules.antecedent.impl;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.service.modules.antecedent.api.Antecedent_Service_api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.entities.En.Antecedent;
import javax.swing.plaf.synth.SynthTextAreaUI;

public class Antecedent_Service_ServiceImpl implements Antecedent_Service_api {
    @Override
    public boolean create(Antecedent antecedent) throws Exception {
        Antecedent_impl antecedent_impl = new Antecedent_impl();
        return antecedent_impl.create(antecedent);
    }

    @Override
    public void update(Antecedent antecedent) throws Exception {
        Antecedent_impl antecedent_impl = new Antecedent_impl();
        antecedent_impl.update(antecedent);
    }

    @Override
    public boolean delete(Antecedent antecedent) throws Exception {
        Antecedent_impl antecedent_impl = new Antecedent_impl();
        return antecedent_impl.delete(antecedent);
    }

    @Override
    public boolean deleteById(Integer id) throws Exception {
        Antecedent_impl antecedent_impl = new Antecedent_impl();
        return antecedent_impl.deleteById(id);
    }

    @Override
    public Antecedent findById(Integer id) throws Exception {
        Antecedent_impl antecedent_impl = new Antecedent_impl();
        return antecedent_impl.findById(id);
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<Antecedent> findAll() throws Exception {
        Antecedent_impl antecedent_impl = new Antecedent_impl();
        return antecedent_impl.findAll();
    }

    public static void main(){
        Antecedent antecedent = new Antecedent();
        antecedent.setNom("fromage rouge");
        antecedent.setCategorie("allergie");
        antecedent.setNiveauRisque(NiveauRisque.Dangereux);
        Antecedent_Service_ServiceImpl ante=new Antecedent_Service_ServiceImpl();
        Antecedent antecedent1=new Antecedent();
        List<Antecedent> lists=new ArrayList<>();
        try{
            lists=ante.findAll();
            for(Antecedent a:lists){
                System.out.println("le nom de l'antecedent est "+a.getNom());
            }
            //antecedent.setIdAntecedent(new Antecedent_impl().get_last_id());
           // antecedent.setNom("blessure_au_jambe");
            //ante.update(antecedent);
            //boolean flag=ante.delete(antecedent);
            Antecedent test=ante.findById(14);
            System.out.println("le nom de l'antecedent numero 14 est "+test.getNom());
           // boolean flag=ante.create(antecedent);
          /*  if(flag){
                System.out.println("succes");
            }
            else{
                System.out.println("erreur");
            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}