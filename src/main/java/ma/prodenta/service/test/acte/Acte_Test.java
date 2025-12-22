package ma.prodenta.service.test.acte;

import ma.prodenta.service.modules.actes.impl.Acte_Service_impl;
import ma.prodenta.entities.En.Acte;
import java.util.List;

public class Acte_Test {
    public static void main(String[] args) {
        Acte_Service_impl acteService = new Acte_Service_impl();
        try {

            String categorie = "Soins";
            String motCle = "Dent";
            List<Acte> actesParCategorie = acteService.getActesParCategorie(categorie);

            System.out.println("Actes par categorie : " + actesParCategorie.size());

            List<Acte> actesParMotCle = acteService.rechercherActesParMotCle(motCle);

            System.out.println("Actes par mot cle : " + actesParMotCle.size());

            double prixMoyen = acteService.calculerPrixMoyenActes();

            System.out.println("Prix moyen des actes : " + prixMoyen);

            boolean existe = acteService.existeActe("Contrôle de routine");

            System.out.println("Acte existe : " + existe);

            Acte actePlusCher = acteService.getActeLePlusCher();

            System.out.println("Acte le plus cher : " + actePlusCher.getLibelle() + " | " + actePlusCher.getPrix_de_base());

            Acte acteMoinsCher = acteService.getActeLeMoinsCher();

            System.out.println("Acte le moins cher : " + acteMoinsCher.getLibelle() + " | " + acteMoinsCher.getPrix_de_base());

            List<Acte> actesTries = acteService.trierActesParPrix(true);

            System.out.println("Nombre d'actes tries : " + actesTries.size());

            System.out.println("TEST ACTE TERMINÉ AVEC SUCCÈS");

        } catch (Exception e) {
            System.err.println("ERREUR TEST ACTE");
            e.printStackTrace();
        }
    }
}


