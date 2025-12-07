package ma.prodenta.repository.modules.admin.implementation;
import ma.prodenta.entities.En.Admin;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.common.Connextion_db;
import ma.prodenta.repository.modules.admin.api.AdminDao;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Admin_impl implements AdminDao {

    public static Connextion_db connetion_base;

    @Override
    public Optional<Admin> findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM admin WHERE username = ?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, username);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return Optional.of(mapAdmin(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Admin> findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM admin WHERE email = ?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return Optional.of(mapAdmin(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT count(*) FROM admin WHERE id = ?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (Exception ignore) {}
        return false;
    }

    @Override
    public long count() {
        String sql = "SELECT count(*) FROM admin";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) return rs.getLong(1);
        } catch (Exception ignore) {}
        return 0;
    }


    @Override
    public List<Admin> findPage(int limit, int offset) {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin LIMIT ? OFFSET ?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, limit);
            pr.setInt(2, offset);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                list.add(mapAdmin(rs));
            }
        } catch (Exception ignore) {}
        return list;
    }

    @Override
    public Optional<Admin> login(String username, String passwordHash) throws Exception {
        String sql = "SELECT * FROM admin WHERE username = ? AND password_hash = ?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, username);
            pr.setString(2, passwordHash);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return Optional.of(mapAdmin(rs));
        }
        return Optional.empty();
    }

    @Override
    public List<Admin> findAll() throws Exception {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapAdmin(rs));
            }
        }
        return list;
    }

    @Override
    public Admin findById(Integer id) throws Exception {
        String sql = "SELECT * FROM admin WHERE id = ?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return mapAdmin(rs);
        }
        return null;
    }


    @Override
    public boolean create(Admin a) throws SQLException {
        String sql = """
            INSERT INTO admin (username,nom,password_hash,email,role,lastLoginDate,idRole) 
            VALUES (?,?,?,?,?,?,?)
        """;
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, a.getUsername());
            pr.setString(2, a.getNom());
            pr.setString(3, a.getPassword_hash());
            pr.setString(4, a.getEmail());
            pr.setString(5, a.getRole());
            pr.setTimestamp(6, a.getLastLoginDate() != null ? Timestamp.valueOf(a.getLastLoginDate()) : null);
            pr.setObject(7, a.getIdRole());

            return pr.executeUpdate() > 0;
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public Utilisateur update(Admin a) {
        String sql = """
            UPDATE admin SET username=?, nom=?, password_hash=?, email=?, role=?, lastLoginDate=?, idRole=? 
            WHERE id = ?
        """;
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, a.getUsername());
            pr.setString(2, a.getNom());
            pr.setString(3, a.getPassword_hash());
            pr.setString(4, a.getEmail());
            pr.setString(5, a.getRole());
            pr.setTimestamp(6, a.getLastLoginDate() != null ? Timestamp.valueOf(a.getLastLoginDate()) : null);
            pr.setObject(7, a.getIdRole());
            pr.setInt(8, a.getId());

            pr.executeUpdate();
        }
        catch(Exception ignored) {}
        return null;
    }

    @Override
    public boolean delete(Admin objet) throws SQLException, IOException {
        return deleteById(objet.getId()) ;
    }


    @Override
    public boolean deleteById(Integer id) throws SQLException, IOException {
        String sql = "DELETE FROM admin WHERE id = ?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    private Admin mapAdmin(ResultSet rs) throws SQLException {
        return Admin.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .nom(rs.getString("nom"))
                .password_hash(rs.getString("password_hash"))
                .email(rs.getString("email"))
                .role(rs.getString("role"))
                .lastLoginDate(rs.getTimestamp("lastLoginDate") != null ?
                        rs.getTimestamp("lastLoginDate").toLocalDateTime() : null)
                .idRole(rs.getObject("idRole") != null ?
                        rs.getInt("idRole") : null)
                .build();
    }
}
