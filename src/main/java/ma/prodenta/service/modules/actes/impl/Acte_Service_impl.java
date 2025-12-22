package ma.prodenta.service.modules.actes.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.service.modules.actes.api.Acte_Service_api;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Acte_Service_impl implements Acte_Service_api {

    @Override
    public List<Acte> getActesParCategorie(String categorie) throws Exception {
        if(categorie == null || categorie.isEmpty()) throw new Exception("Catégorie invalide");
        Acte_impl acteRepository =Application_contexte.getActeRepository();

        List<Acte>actes=acteRepository.getActesParCategorie(categorie);
        if (actes==null || actes.isEmpty()){
            throw new Exception("impossible de retourner des actes pour cette categorie");
        }
        else return actes;
    }

    @Override
    public List<Acte> rechercherActesParMotCle(String motCle) throws Exception {
        if(motCle == null || motCle.isEmpty()) throw new Exception("Mot clé invalide");
        Acte_impl acteRepository =Application_contexte.getActeRepository();
        List<Acte>actes=acteRepository.rechercherActesParMotCle(motCle);
        if (actes==null || actes.isEmpty()){
            throw new Exception("impossible de retourner un acte pour ce mot cle ");
        }
        else return actes;
    }

    @Override
    public double calculerPrixMoyenActes() throws Exception {
       double somme=0;
       Acte_impl acteRepository =Application_contexte.getActeRepository();
        List<Acte> actes = acteRepository.findAll();
        if(actes.isEmpty()) throw new Exception("Aucun acte trouvé");
        for(Acte acte : actes){
            somme+=acte.getPrix_de_base();
        }
        return  somme/actes.size();
    }

    @Override
    public boolean existeActe(String libelle) throws Exception {
        if(libelle == null || libelle.isEmpty()) throw new Exception("Libelle invalide");
        Acte_impl acte_impl=Application_contexte.getActeRepository();
        Acte acte=acte_impl.findbynom(libelle);
        return acte != null;
    }

    @Override
    public Acte getActeLePlusCher() throws Exception {
        Acte_impl acteRepository =Application_contexte.getActeRepository();
        List<Acte> actes=acteRepository.trierActesParPrix(true);
        if(actes==null || actes.isEmpty()){
            throw new Exception("la liste des actes est vides ");
        }
        return actes.getLast();
    }

    @Override
    public Acte getActeLeMoinsCher() throws Exception {
        Acte_impl acteRepository =Application_contexte.getActeRepository();
        List<Acte> actes=acteRepository.trierActesParPrix(true);
        if(actes==null || actes.isEmpty()){
            throw new Exception("la liste des actes est vides ");
        }
        return actes.getFirst();
    }

    @Override
    public List<Acte> trierActesParPrix(boolean ascendant) throws Exception {
        Acte_impl acteRepository =Application_contexte.getActeRepository();
        List<Acte> actes=acteRepository.trierActesParPrix(ascendant);
        if(actes==null || actes.isEmpty()){
            throw new Exception("la liste des actes est vides ");
        }
        return actes;
    }
}
