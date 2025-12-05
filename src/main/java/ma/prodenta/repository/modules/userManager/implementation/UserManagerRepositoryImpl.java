// repository/modules/UserManager/implementation/UserManagerRepositoryImpl.java
package ma.prodenta.repository.modules.userManager.implementation;

import ma.prodenta.entities.En.UserManager;
import ma.prodenta.repository.modules.UserManager.api.UserManagerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagerRepositoryImpl implements UserManagerRepository {

    private final Connection connection;

    public UserManagerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserManager findById(Integer id) throws Exception {
        String sql = "SELECT * FROM user_manager WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public UserManager findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM user_manager WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public List<UserManager> findAll() throws Exception {
        String sql = "SELECT * FROM user_manager";
        List<UserManager> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public UserManager save(UserManager user) throws Exception {
        String sql = "INSERT INTO user_manager " +
                "(username, password_hash, role, actif, date_creation) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setBoolean(4, user.getActif());
            ps.setTimestamp(5, new java.sql.Timestamp(user.getDateCreation().getTime()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) user.setIdUser(keys.getInt(1));
            }
        }
        return user;
    }

    @Override
    public UserManager update(UserManager user) throws Exception {
        String sql = "UPDATE user_manager SET " +
                "username = ?, password_hash = ?, role = ?, actif = ?, date_creation = ? " +
                "WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setBoolean(4, user.getActif());
            ps.setTimestamp(5, new java.sql.Timestamp(user.getDateCreation().getTime()));
            ps.setInt(6, user.getIdUser());
            ps.executeUpdate();
        }
        return user;
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM user_manager WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private UserManager mapRow(ResultSet rs) throws SQLException {
        UserManager u = new UserManager();
        u.setIdUser(rs.getInt("id_user"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRole(rs.getString("role"));
        u.setActif(rs.getBoolean("actif"));
        u.setDateCreation(rs.getTimestamp("date_creation"));
        return u;
    }
}
