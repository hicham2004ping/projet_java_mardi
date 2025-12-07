package ma.prodenta.repository.modules.Facture.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Facture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactureDaoimplTest {

    private FactureDaoimpl factureDao;

    @BeforeEach
    void setUp() throws Exception {
        factureDao = new FactureDaoimpl();

        // Vider la table Facture avant chaque test
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM Facture");
        }
    }

    private Facture buildFactureDemo() {
        return Facture.builder()
                .total(1000.0)
                .totalpaye(400.0)
                .reste(600.0)
                .statut("en attente")
                .dateFact(new Date())
                .idSF(1) // adapte si nécessaire
                .build();
    }

    @Test
    void testCreateAndFindAll() throws Exception {
        // GIVEN
        Facture f = buildFactureDemo();

        // WHEN
        boolean created = factureDao.create(f);
        List<Facture> factures = factureDao.findAll();

        // THEN
        assertTrue(created, "create() doit retourner true quand l'insertion réussit");
        assertEquals(1, factures.size(), "findAll() doit retourner 1 facture après insertion");

        Facture saved = factures.get(0);
        assertEquals(f.getTotal(), saved.getTotal());
        assertEquals(f.getTotalpaye(), saved.getTotalpaye());
        assertEquals(f.getReste(), saved.getReste());
        assertEquals(f.getStatut(), saved.getStatut());
        assertEquals(f.getIdSF(), saved.getIdSF());
        assertNotNull(saved.getIdFact(), "idFact doit être généré (AUTO_INCREMENT)");
    }

    @Test
    void testFindById() throws Exception {
        // GIVEN
        Facture f = buildFactureDemo();
        assertTrue(factureDao.create(f));

        List<Facture> all = factureDao.findAll();
        assertFalse(all.isEmpty(), "La liste ne doit pas être vide après insertion");

        Facture saved = all.get(0);

        // WHEN
        Facture found = factureDao.findById(saved.getIdFact());

        // THEN
        assertNotNull(found, "findById() doit retourner une facture existante");
        assertEquals(saved.getIdFact(), found.getIdFact());
        assertEquals(saved.getTotal(), found.getTotal());
    }

    @Test
    void testUpdate() throws Exception {
        // GIVEN
        Facture f = buildFactureDemo();
        assertTrue(factureDao.create(f));

        Facture saved = factureDao.findAll().get(0);

        // modification
        saved.setTotal(2000.0);
        saved.setTotalpaye(1500.0);
        saved.setReste(500.0);
        saved.setStatut("payee");

        // WHEN
        factureDao.update(saved);
        Facture updated = factureDao.findById(saved.getIdFact());

        // THEN
        assertNotNull(updated);
        assertEquals(2000.0, updated.getTotal());
        assertEquals(1500.0, updated.getTotalpaye());
        assertEquals(500.0, updated.getReste());
        assertEquals("payee", updated.getStatut());
    }

    @Test
    void testDelete() throws Exception {
        // GIVEN
        Facture f = buildFactureDemo();
        assertTrue(factureDao.create(f));

        Facture saved = factureDao.findAll().get(0);

        // WHEN
        boolean deleted = factureDao.delete(saved);
        List<Facture> allAfter = factureDao.findAll();

        // THEN
        assertTrue(deleted, "delete() doit retourner true si une ligne est supprimée");
        assertTrue(allAfter.isEmpty(), "La table Facture doit être vide après suppression");
    }

    @Test
    void testDeleteById() throws Exception {
        // GIVEN
        Facture f = buildFactureDemo();
        assertTrue(factureDao.create(f));

        Facture saved = factureDao.findAll().get(0);
        Integer id = saved.getIdFact();

        // WHEN
        boolean deleted = factureDao.deleteById(id);
        Facture found = factureDao.findById(id);

        // THEN
        assertTrue(deleted, "deleteById() doit retourner true si une ligne est supprimée");
        assertNull(found, "findById() doit retourner null après suppression");
    }
}
