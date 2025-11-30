package ma.prodenta.repository.modules.auth;

import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.config.SessionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public Utilisateur findByLogin(String username) {
        String sql = "SELECT idUser, login, motdepasse, idRole FROM Utilisateur WHERE username = ?";

        try (Connection conn = SessionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToUser(rs);
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Erreur DAO findByLogin", e);
        }
    }

    public Utilisateur findById(int id) {
        String sql = "SELECT idUser, login, motdepasse, idRole FROM Utilisateur WHERE idUser = ?";

        try (Connection conn = SessionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToUser(rs);
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Erreur DAO findById", e);
        }
    }

    public List<Utilisateur> findAll() {
        String sql = "SELECT idUser, login, motdepasse, idRole FROM Utilisateur ORDER BY login";

        List<Utilisateur> Utilisateur = new ArrayList<>();

        try (Connection conn = SessionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Utilisateur.add(mapToUser(rs));
            }

            return Utilisateur;

        } catch (Exception e) {
            throw new RuntimeException("Erreur DAO findAll", e);
        }
    }

    public void save(Utilisateur user) {
        String sql = "INSERT INTO Utilisateur (login, motdepasse, idRole) VALUES (?, ?, ?)";

        try (Connection conn = SessionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getMotdepasse();
            ps.setString(3, user.getIdRole();

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erreur DAO save user", e);
        }
    }

    public void update(Utilisateur user) {
        String sql = "UPDATE Utilisateur SET login = ?, motdepasse = ?, idRole = ? WHERE idUser = ?";

        try (Connection conn = SessionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getMotdepasse();
            ps.setString(3, user.getIdRole();
            ps.setInt(4, user.getIdUser();

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erreur DAO update user", e);
        }
    }

    public void delete(Utilisateur user) {
        String sql = "DELETE FROM Utilisateur WHERE idUser = ?";

        try (Connection conn = SessionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getIdUser());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erreur DAO delete user", e);
        }
    }

    private Utilisateur mapToUser(ResultSet rs) throws SQLException {
        User u = new Utilisateur();
        u.setId(rs.getInt("idUser"));
        u.setLogin(rs.getString("login"));
        u.setPassword(rs.getString("motdepasse"));
        u.setRole(rs.getString("idRole"));
        return u;
    }
}
