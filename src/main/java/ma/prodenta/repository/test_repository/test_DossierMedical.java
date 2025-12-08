package ma.prodenta.repository.test_repository;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;

public class test_DossierMedical {

    // ---------------------- test find_patient() ----------------------
    @Test
    void testFindPatient() throws Exception {
        Dossier_medical_impl dao = new Dossier_medical_impl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt("idDossier")).thenReturn(1);
            when(rs.getInt("idPatient")).thenReturn(5);
            when(rs.getInt("idMedecin")).thenReturn(2);
            when(rs.getDate("dateCreation"))
                    .thenReturn(Date.valueOf(LocalDate.of(2024, 1, 1)));

            DossierMedical ds = dao.find_patient(new Patient_impl().findById(5));

            assertNotNull(ds);
            assertEquals(5, ds.getIdPatient());
            assertEquals(2, ds.getIdMedecin());
        }
    }

    // ---------------------- test findAll() ----------------------
    @Test
    void testFindAll() throws Exception {
        Dossier_medical_impl dao = new Dossier_medical_impl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            // 1 ligne puis fin
            when(rs.next()).thenReturn(true, false);
            when(rs.getInt("idDossier")).thenReturn(1);
            when(rs.getInt("idPatient")).thenReturn(5);
            when(rs.getInt("idMedecin")).thenReturn(2);
            when(rs.getDate("dateCreation"))
                    .thenReturn(Date.valueOf(LocalDate.of(2024, 1, 1)));

            List<DossierMedical> list = dao.findAll();

            assertEquals(1, list.size());
            assertEquals(1, list.get(0).getIdDossier());
        }
    }

    // ---------------------- test findById() ----------------------
    @Test
    void testFindById() throws Exception {
        Dossier_medical_impl dao = new Dossier_medical_impl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt("idDossier")).thenReturn(7);
            when(rs.getInt("idPatient")).thenReturn(9);
            when(rs.getInt("idMedecin")).thenReturn(3);
            when(rs.getDate("dateCreation"))
                    .thenReturn(Date.valueOf(LocalDate.of(2023, 12, 31)));

            DossierMedical ds = dao.findById(7);

            assertNotNull(ds);
            assertEquals(7, ds.getIdDossier());
            assertEquals(9, ds.getIdPatient());
        }
    }

    // ---------------------- test create() ----------------------
    @Test
    void testCreate() throws Exception {
        Dossier_medical_impl dao = new Dossier_medical_impl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        DossierMedical ds = DossierMedical.builder()
                .idPatient(5)
                .idMedecin(2)
                .build();

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            boolean created = dao.create(ds);

            assertTrue(created);
        }
    }

    // ---------------------- test deleteById() ----------------------
    @Test
    void testDeleteById() throws Exception {
        Dossier_medical_impl dao = new Dossier_medical_impl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            boolean deleted = dao.deleteById(10);

            assertTrue(deleted);
        }
    }

    // ---------------------- test delete(DossierMedical) ----------------------
    @Test
    void testDeleteObjet() throws Exception {
        Dossier_medical_impl dao = new Dossier_medical_impl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        DossierMedical ds = DossierMedical.builder()
                .idDossier(15)
                .build();

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            boolean deleted = dao.delete(ds);

            assertTrue(deleted);
        }
    }

    // ---------------------- test get_last_id() ----------------------
    @Test
    void testGetLastId() throws Exception {
        Dossier_medical_impl dao = new Dossier_medical_impl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        SessionFactory sf = mock(SessionFactory.class);
        when(sf.getConnection()).thenReturn(conn);

        try (MockedStatic<SessionFactory> mocked = mockStatic(SessionFactory.class)) {

            mocked.when(SessionFactory::getInstance).thenReturn(sf);

            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(20);

            int id = dao.get_last_id();

            assertEquals(20, id);
        }
    }
}
