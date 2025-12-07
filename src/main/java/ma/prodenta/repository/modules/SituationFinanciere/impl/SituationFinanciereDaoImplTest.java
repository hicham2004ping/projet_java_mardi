package ma.prodenta.repository.modules.SituationFinanciere.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.SituationFinanciere;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SituationFinanciereDaoImplTest {

    private SituationFinanciereDaoImpl dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new SituationFinanciereDaoImpl();

        // IMPORTANT : vider la table avant chaque test
        try (Connection c = SessionFactory.getInstance().getConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM SituationFinanciere");
        }
    }

    private SituationFinanciere buildSF() {
        return SituationFinanciere.builder()
                .totalActes(1500.0)
                .totalPaye(500.0)
                .credit(1000.0)
                .statut("en attente")
                .enPromo("Non")
                .idPatient(1)
                .build();
    }

    @Test
    void testCreateAndFindAll() throws Exception {
        // GIVEN
        SituationFinanciere sf = buildSF();

        // WHEN
        boolean created = dao.create(sf);
        List<SituationFinanciere> list = dao.findAll();

        // THEN
        assertTrue(created, "create() doit retourner true si insertion OK");
        assertEquals(1, list.size(), "findAll() doit retourner UNE ligne après création");

        SituationFinanciere saved = list.get(0);
        assertNotNull(saved.getIdSF(), "idSF doit être auto-généré");
        assertEquals(sf.getTotalActes(), saved.getTotalActes());
        assertEquals(sf.getTotalPaye(), saved.getTotalPaye());
        assertEquals(sf.getCredit(), saved.getCredit());
        assertEquals(sf.getStatut(), saved.getStatut());
        assertEquals(sf.getEnPromo(), saved.getEnPromo());
        assertEquals(sf.getIdPatient(), saved.getIdPatient());
    }

    @Test
    void testFindById() throws Exception {
        // GIVEN
        SituationFinanciere sf = buildSF();
        dao.create(sf);

        List<SituationFinanciere> list = dao.findAll();
        SituationFinanciere saved = list.get(0);

        // WHEN
        SituationFinanciere found = dao.findById(saved.getIdSF().longValue());

        // THEN
        assertNotNull(found, "findById() doit retrouver la SF insérée");
        assertEquals(saved.getIdSF(), found.getIdSF());
        assertEquals(saved.getTotalActes(), found.getTotalActes());
    }

    @Test
    void testUpdate() throws Exception {
        // GIVEN
        SituationFinanciere sf = buildSF();
        dao.create(sf);

        SituationFinanciere saved = dao.findAll().get(0);

        // Modification
        saved.setTotalActes(3000.0);
        saved.setTotalPaye(2500.0);
        saved.setCredit(500.0);
        saved.setStatut("payee");
        saved.setEnPromo("Oui");
        saved.setIdPatient(2);

        // WHEN
        dao.update(saved);
        SituationFinanciere updated = dao.findById(saved.getIdSF().longValue());

        // THEN
        assertNotNull(updated);
        assertEquals(3000.0, updated.getTotalActes());
        assertEquals(2500.0, updated.getTotalPaye());
        assertEquals(500.0, updated.getCredit());
        assertEquals("payee", updated.getStatut());
        assertEquals("Oui", updated.getEnPromo());
        assertEquals(2, updated.getIdPatient());
    }

    @Test
    void testDelete() throws Exception {
        // GIVEN
        SituationFinanciere sf = buildSF();
        dao.create(sf);

        SituationFinanciere saved = dao.findAll().get(0);

        // WHEN
        boolean deleted = dao.delete(saved);
        List<SituationFinanciere> after = dao.findAll();

        // THEN
        assertTrue(deleted, "delete() doit retourner true");
        assertTrue(after.isEmpty(), "La table doit être vide après suppression");
    }

    @Test
    void testDeleteById() throws Exception {
        // GIVEN
        SituationFinanciere sf = buildSF();
        dao.create(sf);

        SituationFinanciere saved = dao.findAll().get(0);
        Long id = saved.getIdSF().longValue();

        // WHEN
        boolean deleted = dao.deleteById(id);
        SituationFinanciere found = dao.findById(id);

        // THEN
        assertTrue(deleted, "deleteById() doit retourner true");
        assertNull(found, "findById() doit retourner null après suppression");
    }
}

