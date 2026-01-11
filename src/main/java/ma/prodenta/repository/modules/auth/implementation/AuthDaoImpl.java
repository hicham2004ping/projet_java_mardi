package ma.prodenta.repository.modules.auth.implementation;
import ma.prodenta.config.SessionFactory;
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

        try (Connection con= SessionFactory.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new UserImpl().mapUtilisateur(rs));
            }
            return Optional.empty();

        }




    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";

        try (Connection con = SessionFactory.getInstance().getConnection();
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
        String sql = "SELECT * FROM utilisateur WHERE login = ?";

        try (Connection con =SessionFactory.getInstance().getConnection();
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
    public boolean existsByLogin(String login) {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE login = ?";

        try (Connection con =SessionFactory.getInstance().getConnection();
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

        try (Connection con =SessionFactory.getInstance().getConnection();
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

        try (Connection con = SessionFactory.getInstance().getConnection();
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

        try (Connection con = SessionFactory.getInstance().getConnection();
             Statement st = con.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new UserImpl().mapUtilisateur(rs));
            }
            return list;
        }
    }

    @Override
    public Utilisateur findById(Integer idUser) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE idUser = ?";

        try (Connection con = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UserImpl().mapUtilisateur(rs);
            }
        }
        return null;
    }


    @Override
    public boolean create(Utilisateur u) throws SQLException, IOException {

        String sqlMaxId = "SELECT IFNULL(MAX(idUser), 0) + 1 FROM utilisateur";

        String sqlInsert = "INSERT INTO utilisateur (" +
                "idUser, nom, email, adresse, cin, tel, idSexe, login, motdepasse, " +
                "dateNaissance, lastLoginDate, idRole" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = SessionFactory.getInstance().getConnection();) {

            // =============================
            // 1) Récupérer le prochain ID
            // =============================
            int nextId = 1;
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sqlMaxId)) {
                if (rs.next()) {
                    nextId = rs.getInt(1);
                }
            }

            // =============================
            // 2) Insérer l'utilisateur
            // =============================
            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {

                ps.setInt(1, nextId);
                ps.setString(2, u.getNom());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getAdresse());
                ps.setString(5, u.getCin());
                ps.setString(6, u.getTel());
                ps.setObject(7, u.getIdSexe(), java.sql.Types.INTEGER);
                ps.setString(8, u.getLogin());
                ps.setString(9, u.getMotdepasse());

                // dateNaissance (LocalDate → java.sql.Date)
                if (u.getDateNaissance() != null)
                    ps.setDate(10, java.sql.Date.valueOf(u.getDateNaissance()));
                else
                    ps.setNull(10, java.sql.Types.DATE);

                // lastLoginDate (LocalDateTime → Timestamp)
                if (u.getLastLoginDate() != null)
                    ps.setTimestamp(11, Timestamp.valueOf(u.getLastLoginDate()));
                else
                    ps.setNull(11, java.sql.Types.TIMESTAMP);

                ps.setObject(12, u.getIdRole(), java.sql.Types.INTEGER);

                int affected = ps.executeUpdate();
                return affected > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void update(Utilisateur u) throws SQLException, IOException, Exception {

        String sql = "UPDATE utilisateur SET " +
                "nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, idSexe = ?, " +
                "login = ?, motdepasse = ?, dateNaissance = ?, lastLoginDate = ?, idRole = ? " +
                "WHERE idUser = ?";

        try (Connection con = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getAdresse());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getTel());
            ps.setObject(6, u.getIdSexe(), java.sql.Types.INTEGER);
            ps.setString(7, u.getLogin());
            ps.setString(8, u.getMotdepasse());

            // LocalDate
            if (u.getDateNaissance() != null)
                ps.setDate(9, java.sql.Date.valueOf(u.getDateNaissance()));
            else
                ps.setNull(9, java.sql.Types.DATE);

            // LocalDateTime
            if (u.getLastLoginDate() != null)
                ps.setTimestamp(10, Timestamp.valueOf(u.getLastLoginDate()));
            else
                ps.setNull(10, java.sql.Types.TIMESTAMP);

            ps.setObject(11, u.getIdRole(), java.sql.Types.INTEGER);

            ps.setInt(12, u.getIdUser());

            ps.executeUpdate();
        }
    }


    @Override
    public boolean delete(Utilisateur u) throws SQLException, Exception {
        return deleteById(u.getIdUser());
    }


    @Override
    public boolean deleteById(Integer idUser) throws SQLException, Exception {
        String sql = "DELETE FROM utilisateur WHERE idUser = ?";

        try (Connection con = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }


    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}