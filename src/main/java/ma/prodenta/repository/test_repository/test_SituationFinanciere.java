package ma.prodenta.repository.test_repository;
import ma.prodenta.entities.En.SituationFinanciere;
import ma.prodenta.repository.modules.SituationFinanciere.impl.SituationFinanciereDaoImpl;
import ma.prodenta.config.SessionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.sql.*;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class test_SituationFinanciere {
    @Test
    void testFindAll() throws Exception {

        SituationFinanciereDaoImpl dao = new SituationFinanciereDaoImpl();

        Connection c = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(c);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {
            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(c.prepareStatement("SELECT * FROM SituationFinanciere")).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true, false);
            when(rs.getInt("idSF")).thenReturn(1);
            when(rs.getDouble("totalActes")).thenReturn(100.0);
            when(rs.getDouble("totalPaye")).thenReturn(50.0);
            when(rs.getDouble("credit")).thenReturn(50.0);
            when(rs.getString("statut")).thenReturn("payee");
            when(rs.getString("enPromo")).thenReturn("Non");
            when(rs.getInt("idPatient")).thenReturn(10);

            List<SituationFinanciere> list = dao.findAll();

            assertEquals(1, list.size());
        }
    }


    @Test
    void testFindById() throws Exception {
        SituationFinanciereDaoImpl dao = new SituationFinanciereDaoImpl();

        Connection c = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(c);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {
            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(c.prepareStatement("SELECT * FROM SituationFinanciere WHERE idSF = ?")).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt("idSF")).thenReturn(2);
            when(rs.getDouble("totalActes")).thenReturn(200.0);
            when(rs.getDouble("totalPaye")).thenReturn(150.0);
            when(rs.getDouble("credit")).thenReturn(50.0);
            when(rs.getString("statut")).thenReturn("en attente");
            when(rs.getString("enPromo")).thenReturn("Oui");
            when(rs.getInt("idPatient")).thenReturn(20);
            SituationFinanciere sf1 = dao.findById(2L);

            assertNotNull(sf1);
            assertEquals(200.0, sf1.getTotalActes());
        }
    }
}
