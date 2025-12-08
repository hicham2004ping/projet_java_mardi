package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.modules.RendezVous.fileBase_implementation.RDVDAOImpl;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class test_RDV {

    public static void Testrdv() {
        try {
            RDVDAOImpl rdvDAO = new RDVDAOImpl();

            // Création d'un RDV
            Date dateRDV = new Date(104, 8, 9); // 9 septembre 2004
            Time heure = Time.valueOf("14:30:45");

            RDV rdv = new RDV();
            rdv.setIddossier(1);
            rdv.setDateRDV(dateRDV);
            rdv.setHeure(heure);
            rdv.setMotif("Trois dents cassées");
            rdv.setNoteMedecin("Besoin d'une intervention rapide");

            // Insertion dans la base
            boolean success = rdvDAO.create(rdv);
            if (success) {
                System.out.println("RDV créé avec succès !");
            } else {
                System.out.println("Erreur lors de la création du RDV.");
            }

            // Affichage de tous les RDV
            List<RDV> rdvs = rdvDAO.findAll();
            System.out.println("Liste des RDV existants :");
            for (RDV r : rdvs) {
                System.out.println(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Testrdv();
    }
}
