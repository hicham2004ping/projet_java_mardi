package ma.prodenta.repository.modules.auth;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.repository.common.CrudRepository;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.config.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository <Utilisateur, Integer> {

    @Override
    public Utilisateur save(Utilisateur user) {
        String sql = "INSERT INTO utilisateur(login, motdepasse, idRole) VALUES(?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getMotdepasse());
            stmt.setString(3, user.getIdRole().toString());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                user.setIdUser(keys.getInt(1));
            }

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Utilisateur update(Utilisateur user) {
        String sql = "UPDATE utilisateur SET login=?, motdepasse=?, idRole=? WHERE idUser=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getMotdepasse());
            stmt.setString(3, user.getIdRole().toString());
            stmt.setInt(4, user.getIdUser());

            stmt.executeUpdate();
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Utilisateur objet) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM utilisateur WHERE idUser=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Utilisateur findById(Integer id) {
        String sql = "SELECT * FROM utilisateur WHERE idUser=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Utilisateur u = mapToUtilisateur(rs);
                //return Optional.of(u);
            }

            //return Optional.empty();

        } catch (Exception e) {
            e.printStackTrace();
            //return Optional.empty();
        }
        return null;
    }

    @Override
    public boolean create(Utilisateur objet) throws SQLException {
        return false;
    }

    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM utilisateur";
        List<Utilisateur> utilisateur = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                utilisateur.add(mapToUtilisateur(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    // Méthode utile pour login
    public Optional<Utilisateur> findByUtilisateurname(String login) {
        String sql = "SELECT * FROM utilisateur WHERE login=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToUtilisateur(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Utilisateur mapToUtilisateur(ResultSet rs) throws Exception {
    a