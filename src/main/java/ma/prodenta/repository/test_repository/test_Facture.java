package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Facture;
import ma.prodenta.repository.modules.Facture.impl.FactureDaoimpl;
import ma.prodenta.config.SessionFactory;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class test_Facture {


    @Test
    void testFindAll() throws Exception {

        FactureDaoimpl dao = new FactureDaoimpl();

        Connection conn = mock(Connection.class);
        Statement st = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.createStatement()).thenReturn(st);
            when(st.executeQuery("SELECT * FROM Facture")).thenReturn(rs);

            // simulate 1 result
            when(rs.next()).thenReturn(true, false);
            when(rs.getInt("idFact")).thenReturn(1);
            when(rs.getDouble("total")).thenReturn(500.0);
            when(rs.getDouble("totalpaye")).thenReturn(300.0);
            when(rs.getDouble("reste")).thenReturn(200.0);
            when(rs.getString("statut")).thenReturn("payee");
            when(rs.getDate("dateFact")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
            when(rs.getInt("idSF")).thenReturn(10);

            List<Facture> list = dao.findAll();

            assertEquals(1, list.size());
            assertEquals(500.0, list.get(0).getTotal());
        }
    }

    // ---------------------------- TEST findById() ----------------------------
    @Test
    void testFindById() throws Exception {

        FactureDaoimpl dao = new FactureDaoimpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement("SELECT * FROM Facture WHERE idFact = ?"))
                    .thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt("idFact")).thenReturn(2);
            when(rs.getDouble("total")).thenReturn(400.0);
            when(rs.getDouble("totalpaye")).thenReturn(200.0);
            when(rs.getDouble("reste")).thenReturn(200.0);
            when(rs.getString("statut")).thenReturn("en attente");
            when(rs.getDate("dateFact")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
            when(rs.getInt("idSF")).thenReturn(5);

            Facture f = dao.findById(2);

            assertNotNull(f);
            assertEquals(400.0, f.getTotal());
        }
    }

    // ---------------------------- TEST create() ----------------------------
    @Test
    void testCreate() throws Exception {

        FactureDaoimpl dao = new FactureDaoimpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        Facture f = Facture.builder()
                .total(300.0)
                .totalpaye(100.0)
                .reste(200.0)
                .statut("non payee")
                .dateFact(new java.util.Date())
                .idSF(7)
                .build();

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            boolean created = dao.create(f);

            assertTrue(created);
        }
    }

    // ---------------------------- TEST update() ----------------------------
    @Test
    void testUpdate() throws Exception {

        FactureDaoimpl dao = new FactureDaoimpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        Facture f = Facture.builder()
                .idFact(10)
                .total(800.0)
                .totalpaye(600.0)
                .reste(200.0)
                .statut("payee")
                .dateFact(new java.util.Date())
                .idSF(3)
                .build();

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);

            dao.update(f);

            verify(ps, times(1)).executeUpdate();
        }
    }

    // ---------------------------- TEST deleteById() ----------------------------
    @Test
    void testDeleteById() throws Exception {

        FactureDaoimpl dao = new FactureDaoimpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement("DELETE FROM Facture WHERE idFact = ?"))
                    .thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            boolean deleted = dao.deleteById(5);

            assertTrue(deleted);
        }
    }
}
