package ma.prodenta.service.modules.actes.impl;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.service.modules.actes.api.Acte_Service_api;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Acte_Service_impl implements Acte_Service_api {

    private final Acte_impl acteRepository;

    public Acte_Service_impl() {
        this.acteRepository = Application_contexte.getActeRepository();
    }

    @Override
    public List<Acte> getActesParCategorie(String categorie) throws Exception {
        if(categorie == null || categorie.isEmpty()) throw new Exception("Catégorie invalide");
        return acteRepository.findAll().stream()
                .filter(a -> a.getCategorie().equalsIgnoreCase(categorie))
                .collect(Collectors.toList());
    }

    @Override
    public List<Acte> rechercherActesParMotCle(String motCle) throws Exception {
        if(motCle == null || motCle.isEmpty()) throw new Exception("Mot clé invalide");
        return acteRepository.findAll().stream()
                .filter(a -> a.getLibelle().toLowerCase().contains(motCle.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public double calculerPrixMoyenActes() throws Exception {
        List<Acte> actes = acteRepository.findAll();
        if(actes.isEmpty()) throw new Exception("Aucun acte trouvé");
        return actes.stream().mapToDouble(Acte::getPrix_de_base).average().orElse(0);
    }

    @Override
    public boolean existeActe(String libelle) throws Exception {
        return acteRepository.findAll().stream()
                .anyMatch(a -> a.getLibelle().equalsIgnoreCase(libelle));
    }

    @Override
    public Acte getActeLePlusCher() throws Exception {
        return acteRepository.findAll().stream()
                .max(Comparator.comparingDouble(Acte::getPrix_de_base))
                .orElseThrow(() -> new Exception("Aucun acte trouvé"));
    }

    @Override
    public Acte getActeLeMoinsCher() throws Exception {
        return acteRepository.findAll().stream()
                .min(Comparator.comparingDouble(Acte::getPrix_de_base))
                .orElseThrow(() -> new Exception("Aucun acte trouvé"));
    }

    @Override
    public List<Acte> trierActesParPrix(boolean ascendant) throws Exception {
        return acteRepository.findAll().stream()
                .sorted(ascendant
                        ? Comparator.comparingDouble(Acte::getPrix_de_base)
                        : Comparator.comparingDouble(Acte::getPrix_de_base).reversed())
                .collect(Collectors.toList());
    }
}
