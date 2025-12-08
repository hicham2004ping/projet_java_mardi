package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.user.implementation.UserImpl;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;

public class test_User {

    // -------------------------- TEST getUser() --------------------------
    @Test
    void testGetUser() throws Exception {
        UserImpl dao = new UserImpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<DriverManager> dmMock = mockStatic(DriverManager.class)) {

            dmMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(conn);

            when(conn.prepareStatement("select * from utilisateur where login=? and motdepasse=?"))
                    .thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true, false);
            when(rs.getInt("idUser")).thenReturn(1);
            when(rs.getString("nom")).thenReturn("Ali");
            when(rs.getString("email")).thenReturn("ali@test.com");
            when(rs.getString("adresse")).thenReturn("Rabat");
            when(rs.getString("cin")).thenReturn("AA12345");
            when(rs.getString("tel")).thenReturn("0600000000");
            when(rs.getInt("idsexe")).thenReturn(1);
            when(rs.getString("login")).thenReturn("ali");
            when(rs.getString("motdepasse")).thenReturn("1234");
            when(rs.getDate("dateNaissance"))
                    .thenReturn(java.sql.Date.valueOf(LocalDate.of(1995, 1, 1)));
            Timestamp ts = Timestamp.valueOf(LocalDateTime.of(2024, 1, 1, 10, 0));
            when(rs.getTimestamp("lastLoginDate")).thenReturn(ts);
            when(rs.getInt("idrole")).thenReturn(2);

            Utilisateur u = dao.getUser("ali", "1234");

            assertNotNull(u);
            assertEquals("Ali", u.getNom());
            assertEquals("ali", u.getLogin());
        }
    }

    // -------------------------- TEST findAll() --------------------------
    @Test
    void testFindAll() throws Exception {
        UserImpl dao = new UserImpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<DriverManager> dmMock = mockStatic(DriverManager.class)) {

            dmMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(conn);

            when(conn.prepareStatement("select * from utilisateur")).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true, false);
            when(rs.getInt("idUser")).thenReturn(1);
            when(rs.getString("nom")).thenReturn("Ali");
            when(rs.getString("email")).thenReturn("ali@test.com");
            when(rs.getString("adresse")).thenReturn("Rabat");
            when(rs.getString("cin")).thenReturn("AA12345");
            when(rs.getString("tel")).thenReturn("0600000000");
            when(rs.getInt("idsexe")).thenReturn(1);
            when(rs.getString("login")).thenReturn("ali");
            when(rs.getString("motdepasse")).thenReturn("1234");
            when(rs.getDate("dateNaissance"))
                    .thenReturn(java.sql.Date.valueOf(LocalDate.of(1995, 1, 1)));
            when(rs.getTimestamp("lastLoginDate"))
                    .thenReturn(Timestamp.valueOf(LocalDateTime.of(2024, 1, 1, 10, 0)));
            when(rs.getInt("idrole")).thenReturn(2);

            List<Utilisateur> users = dao.findAll();

            assertEquals(1, users.size());
            assertEquals("Ali", users.get(0).getNom());
        }
    }

    // -------------------------- TEST findById() --------------------------
    @Test
    void testFindById() throws Exception {
        UserImpl dao = new UserImpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<DriverManager> dmMock = mockStatic(DriverManager.class)) {

            dmMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(conn);

            when(conn.prepareStatement("select * from utilisateur where idUser=?"))
                    .thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt("idUser")).thenReturn(5);
            when(rs.getString("nom")).thenReturn("Sara");
            when(rs.getString("email")).thenReturn("sara@test.com");
            when(rs.getString("adresse")).thenReturn("Casa");
            when(rs.getString("cin")).thenReturn("BB99999");
            when(rs.getString("tel")).thenReturn("0700000000");
            when(rs.getInt("idsexe")).thenReturn(2);
            when(rs.getString("login")).thenReturn("sara");
            when(rs.getString("motdepasse")).thenReturn("abcd");
            when(rs.getDate("dateNaissance"))
                    .thenReturn(java.sql.Date.valueOf(LocalDate.of(1998, 5, 20)));
            when(rs.getTimestamp("lastLoginDate"))
                    .thenReturn(Timestamp.valueOf(LocalDateTime.of(2024, 2, 2, 12, 0)));
            when(rs.getInt("idrole")).thenReturn(3);

            Utilisateur u = dao.findById(5);

            assertNotNull(u);
            assertEquals(5, u.getIdUser());
            assertEquals("Sara", u.getNom());
        }
    }

    // -------------------------- TEST create() --------------------------
    @Test
    void testCreateUser_WhenNotExists() throws Exception {
        UserImpl dao = new UserImpl();

        Connection conn = mock(Connection.class);
        PreparedStatement psSelect = mock(PreparedStatement.class);
        PreparedStatement psInsert = mock(PreparedStatement.class);
        ResultSet rsSelect = mock(ResultSet.class);

        Utilisateur user = Utilisateur.builder()
                .idUser(10)
                .nom("Karim")
                .email("karim@test.com")
                .adresse("Fès")
                .cin("CC12345")
                .tel("0611111111")
                .idSexe(1)
                .login("karim")
                .motdepasse("pass")
                .dateNaissance(LocalDate.of(1990, 1, 1))
                .idRole(2)
                .build();

        try (MockedStatic<DriverManager> dmMock = mockStatic(DriverManager.class)) {

            dmMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(conn);

            // findById -> pas d'utilisateur
            when(conn.prepareStatement("select * from utilisateur where idUser=?"))
                    .thenReturn(psSelect);
            when(psSelect.executeQuery()).thenReturn(rsSelect);
            when(rsSelect.next()).thenReturn(false);

            // insert
            when(conn.prepareStatement("insert into utilisateur values(?,?,?,?,?,?,?,?,?,?,?,?) "))
                    .thenReturn(psInsert);
            doNothing().when(psInsert).execute();

            boolean created = dao.create(user);

            assertTrue(created);
        }
    }

    // -------------------------- TEST deleteById() --------------------------
    @Test
    void testDeleteById() throws Exception {
        UserImpl dao = new UserImpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        try (MockedStatic<DriverManager> dmMock = mockStatic(DriverManager.class)) {

            dmMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(conn);

            when(conn.prepareStatement("delete from utilisateur where idUser=?"))
                    .thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            boolean deleted = dao.deleteById(3);

            assertTrue(deleted);
        }
    }

    // -------------------------- TEST delete(Utilisateur) --------------------------
    @Test
    void testDeleteUser() throws Exception {
        UserImpl dao = new UserImpl();

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        Utilisateur user = Utilisateur.builder()
                .idUser(4)
                .build();

        try (MockedStatic<DriverManager> dmMock = mockStatic(DriverManager.class)) {

            dmMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(conn);

            when(conn.prepareStatement("delete from utilisateur where idUser=?"))
                    .thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            boolean deleted = dao.delete(user);

            assertTrue(deleted);
        }
    }
}
