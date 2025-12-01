package ma.prodenta.repository.modules.admin;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Admin;
import ma.prodenta.repository.common.AdminRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 JDBC
 SessionFactory
 */
public class AdminRepositoryImpl implements AdminRepository {

    private final SessionFactory sessionFactory;

    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        String sql = "SELECT id, username, password_hash, nom, email, role, last_login_at " +
                "FROM admin WHERE username = ?";

        try (Connection connection = sessionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Admin admin = mapRowToAdmin(rs);
                    return Optional.of(admin);
                }
            }
        } catch (SQLException e) {
            // Ici tu peux logger avec ton système de log
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateLastLogin(Long adminId) {
        String sql = "UPDATE admin SET last_login_at = ? WHERE id = ?";

        try (Connection connection = sessionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(2, adminId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
      transforme ResultSet en objet Admin.
     */
    private Admin mapRowToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId((int) rs.getLong("id"));
        admin.setUsername(rs.getString("username"));
        admin.setPasswordHash(rs.getString("password_hash"));
        admin.setNom(rs.getString("nom"));
        admin.setEmail(rs.getString("email"));
        admin.setRole(rs.getString("role"));
        Timestamp ts = rs.getTimestamp("last_login_at");
        if (ts != null) {
            admin.setLastLoginAt(ts.toLocalDateTime());
        }
        return admin;
    }
}
