package ma.prodenta.service.modules.actes.impl;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.service.modules.actes.api.Acte_Service_api;
import java.util.List;

public class Acte_Service_impl implements Acte_Service_api {

    @Override
    public List<Acte> getActesParCategorie(String categorie) throws Exception {
        if (categorie == null || categorie.isEmpty())
            throw new Exception("Catégorie invalide");
        Acte_impl acteRepository = Application_contexte.getActeRepository();

        List<Acte> actes = acteRepository.getActesParCategorie(categorie);
        if (actes == null || actes.isEmpty()) {
            throw new Exception("impossible de retourner des actes pour cette categorie");
        } else
            return actes;
    }

    @Override
    public List<Acte> rechercherActesParMotCle(String motCle) throws Exception {
        if (motCle == null || motCle.isEmpty())
            throw new Exception("Mot clé invalide");
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        List<Acte> actes = acteRepository.rechercherActesParMotCle(motCle);
        if (actes == null || actes.isEmpty()) {
            throw new Exception("impossible de retourner un acte pour ce mot cle ");
        } else
            return actes;
    }

    @Override
    public double calculerPrixMoyenActes() throws Exception {
        double somme = 0;
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        List<Acte> actes = acteRepository.findAll();
        if (actes.isEmpty())
            throw new Exception("Aucun acte trouvé");
        for (Acte acte : actes) {
            somme += acte.getPrix_de_base();
        }
        return somme / actes.size();
    }

    @Override
    public boolean existeActe(String libelle) throws Exception {
        if (libelle == null || libelle.isEmpty())
            throw new Exception("Libelle invalide");
        Acte_impl acte_impl = Application_contexte.getActeRepository();
        Acte acte = acte_impl.findbynom(libelle);
        return acte != null;
    }

    @Override
    public Acte getActeLePlusCher() throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        List<Acte> actes = acteRepository.trierActesParPrix(true);
        if (actes == null || actes.isEmpty()) {
            throw new Exception("la liste des actes est vides ");
        }
        return actes.getLast();
    }

    @Override
    public Acte getActeLeMoinsCher() throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        List<Acte> actes = acteRepository.trierActesParPrix(true);
        if (actes == null || actes.isEmpty()) {
            throw new Exception("la liste des actes est vides ");
        }
        return actes.getFirst();
    }

    @Override
    public List<Acte> trierActesParPrix(boolean ascendant) throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        List<Acte> actes = acteRepository.trierActesParPrix(ascendant);
        if (actes == null || actes.isEmpty()) {
            throw new Exception("la liste des actes est vides ");
        }
        return actes;
    }

    @Override
    public List<Acte> getAllActes() throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        return acteRepository.findAll();
    }

    @Override
    public boolean ajouterActe(Acte acte) throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        if (acte.getId() == 0) {
            try {
                acte.setId(acteRepository.get_last_id());
            } catch (Exception e) {
                // If get_last_id fails or returns 0, we might need a fallback, but assuming it
                // works.
                throw new Exception("Erreur lors de la génération de l'ID", e);
            }
        }
        return acteRepository.create(acte);
    }

    @Override
    public void modifierActe(Acte acte) throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        acteRepository.update(acte);
    }

    @Override
    public boolean supprimerActe(Acte acte) throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        return acteRepository.delete(acte);
    }

    @Override
    public boolean supprimerActeParId(int id) throws Exception {
        Acte_impl acteRepository = Application_contexte.getActeRepository();
        return acteRepository.deleteById(id);
    }
}
