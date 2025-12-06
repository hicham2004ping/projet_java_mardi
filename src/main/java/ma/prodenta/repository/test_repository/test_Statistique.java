package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Charges;
import ma.prodenta.entities.En.Revenus;
import ma.prodenta.repository.modules.statistiques.fileBase_implementation.ChargesDAOImpl;
import ma.prodenta.repository.modules.statistiques.fileBase_implementation.RevenusDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class test_Statistique {

    public static void test_charge() {
        try {
            System.out.println("******** TEST COMPLET CHARGES ********");

            ChargesDAOImpl chargesDAO = new ChargesDAOImpl();

            // --- Création ---
            System.out.println("\n--- Création Charge ---");
            Charges c = new Charges();
            c.setTitre("Achat matériel");
            c.setDescription("Achat de matériel dentaire");
            c.setMontant(2500.00);
            c.setDateCharge(new Date()); // date actuelle
            c.setIdCabinet(1);

            boolean created = chargesDAO.create(c);
            if (!created) {
                System.out.println("Erreur lors de la création de la charge");
                return;
            }

            Long lastId = chargesDAO.get_last_id();
            System.out.println("Charge créée, ID = " + lastId);

            // --- Lecture ---
            System.out.println("\n--- Lecture Charge ---");
            Charges chargeLu = chargesDAO.findById(lastId);
            if (chargeLu != null) {
                System.out.println("Charge lue : " + chargeLu.getTitre() + " - " + chargeLu.getDescription() + " - Montant : " + chargeLu.getMontant());
            } else {
                System.out.println("Charge introuvable");
            }

            // --- Modification ---
            System.out.println("\n--- Modification Charge ---");
            chargeLu.setMontant(2700.00);
            chargeLu.setDescription("Matériel dentaire mis à jour");
            chargesDAO.update(chargeLu);

            // Lecture après modification
            Charges chargeUpdated = chargesDAO.findById(lastId);
            System.out.println("Charge modifiée : " + chargeUpdated.getTitre() + " - " + chargeUpdated.getMontant());

            // --- Lecture de toutes les charges ---
            System.out.println("\n--- Liste de toutes les Charges ---");
            List<Charges> allCharges = chargesDAO.findAll();
            for (Charges ch : allCharges) {
                System.out.println("[ID " + ch.getIdCharge() + "] Cabinet " + ch.getIdCabinet()
                        + " | " + ch.getDateCharge() + " | " + ch.getTitre()
                        + " - " + ch.getDescription() + " | Montant : " + ch.getMontant());
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans le test Charge : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void test_revenus() {
        try {
            System.out.println("******** Test Revenus ********");

            RevenusDAOImpl revenusDAO = new RevenusDAOImpl();
            Revenus r = new Revenus();

            r.setType("revenus");
            r.setDescription("Test auto : ABDOU devient millionaire");
            r.setMontant(9000.00);
            r.setDateRev(java.sql.Date.valueOf(LocalDate.now()));
            r.setIdCabinet(1);

            System.out.println("ID Cabinet envoyé = " + r.getIdCabinet());

            boolean created = revenusDAO.create(r);

            if (created) {
                Long lastId = revenusDAO.get_last_id();
                System.out.println("Revenu créé avec succès, ID : " + lastId);

                Revenus rLu = revenusDAO.findById(lastId);
                System.out.println("Lecture revenu : " + rLu.getDescription() + " - " + rLu.getMontant());

                // Modification
                rLu.setMontant(9500.00);
                rLu.setDescription("Revenu modifié pour test");
                revenusDAO.update(rLu);

                // Lecture après modification
                Revenus rUpdated = revenusDAO.findById(lastId);
                System.out.println("Revenu modifié : nouveau montant = " + rUpdated.getMontant());


            } else {
                System.out.println("Erreur lors de la création du revenu");
            }

        } catch (Exception e) {
            System.out.println("Erreur dans le test Revenus : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        test_charge();
    }
}
