package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Facture;
import ma.prodenta.repository.modules.Facture.impl.FactureDaoimpl;

import java.util.Date;
import java.util.List;

public class test_Facture {

    public static void testFacture() {
        try {

            FactureDaoimpl factureDAO = new FactureDaoimpl();

            // ----------------------------
            // 1. Création d'une facture
            // ----------------------------
            Facture f = new Facture();
            f.setTotal(1500.0);
            f.setTotalpaye(500.0);
            f.setReste(1000.0);
            f.setStatut("en attente");
            f.setDateFact(new Date());
            f.setIdSF(1); // situation financière existante

            boolean created = factureDAO.create(f);
            if (created) {
                System.out.println("Facture créée avec succès !");
            } else {
                System.out.println("Erreur lors de la création de la facture.");
            }

            // ----------------------------
            // 2. Affichage de toutes les factures
            // ----------------------------
            List<Facture> list = factureDAO.findAll();
            System.out.println("\n📄 Liste des factures :");
            list.forEach(System.out::println);

            // ----------------------------
            // 3. Récupération de la dernière facture
            // ----------------------------
            int lastId = factureDAO.get_last_id();
            Facture f2 = factureDAO.findById(lastId);

            System.out.println("\n📌 Facture trouvée par ID (" + lastId + ") :");
            System.out.println(f2);

            // ----------------------------
            // 4. Mise à jour de la facture
            // ----------------------------
            f2.setTotalpaye(1500.0);
            f2.setReste(0.0);
            f2.setStatut("payee");

            factureDAO.update(f2);

            System.out.println("\n✔ Facture mise à jour !");
            System.out.println(factureDAO.findById(lastId));

            // ----------------------------
            // 5. Suppression de la facture
            // ----------------------------
            boolean deleted = factureDAO.deleteById(lastId);
            if (deleted) {
                System.out.println("\n🗑 Facture supprimée avec succès !");
            } else {
                System.out.println("\n❌ Erreur lors de la suppression !");
            }

            // Vérification finale
            System.out.println("\n📄 Liste finale des factures :");
            factureDAO.findAll().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testFacture();
    }
}
