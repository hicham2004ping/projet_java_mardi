package ma.prodenta.repository.modules.auth.implementation;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.common.Connextion_db;
import ma.prodenta.repository.modules.auth.api.AuthDao;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.repository.modules.user.implementation.UserImpl;

public class AuthDaoImpl implements AuthDao {

    // ==========================
    //  Méthodes spécifiques Auth
    // ==========================

    @Override
    public Optional<Utilisateur> findByLogin(String login) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE login = ?";

        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new UserImpl().mapUtilisateur(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";

        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new UserImpl().mapUtilisateur(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Utilisateur> login(String login, String motdepasse) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE login = ? AND motdepasse = ?";

        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, motdepasse);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new UserImpl().mapUtilisateur(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByLogin(String login) {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE login = ?";

        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception ignored) {}
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";

        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception ignored) {}
        return false;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM utilisateur";

        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword());
             Statement st = con.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception ignored) {}
        return 0;
    }

    // ==========================
    //  Méthodes CrudRepository
    // ==========================

    @Override
    public List<Utilisateur> findAll() throws Exception {
        List<Utilisateur> list = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";

        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword());
             Statement st = con.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new UserImpl().mapUtilisateur(rs));
            }
            return list;
        }
    }

    @Override
    public Utilisateur findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public boolean create(Utilisateur objet) throws SQLException, IOException {
        return false;
    }

    @Override
    public void update(Utilisateur objet) throws SQLException, IOException, Exception {

    }

    @Override
    public boolean delete(Utilisateur objet) throws SQLException, Exception {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException, Exception {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}