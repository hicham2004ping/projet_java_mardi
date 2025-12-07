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

public class Auth_impl implements AuthDao {

    // --------------------------------------------
    @Override
    public Optional<Utilisateur> findByLogin(String login) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE login=?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, login);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return Optional.of(mapUtilisateur(rs));
        }
        return Optional.empty();
    }

    // --------------------------------------------
    @Override
    public Optional<Utilisateur> findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE email=?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return Optional.of(mapUtilisateur(rs));
        }
        return Optional.empty();
    }

    // --------------------------------------------
    @Override
    public boolean existsByLogin(String login) {
        String sql = "SELECT count(*) FROM utilisateur WHERE login=?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, login);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (Exception ignore) {}
        return false;
    }

    // --------------------------------------------
    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT count(*) FROM utilisateur WHERE email=?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (Exception ignore) {}
        return false;
    }

    // --------------------------------------------
    @Override
    public long count() {
        String sql = "SELECT count(*) FROM utilisateur";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) return rs.getLong(1);
        } catch (Exception ignore) {}
        return 0;
    }

    // --------------------------------------------
    // LOGIN
    // --------------------------------------------
    @Override
    public Optional<Utilisateur> login(String login, String motdepasse) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE login=? AND motdepasse=?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, login);
            pr.setString(2, motdepasse);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return Optional.of(mapUtilisateur(rs));
        }
        return Optional.empty();
    }

    // --------------------------------------------
    @Override
    public List<Utilisateur> findAll() throws Exception {
        List<Utilisateur> list = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(mapUtilisateur(rs));
        }
        return list;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    // --------------------------------------------
    @Override
    public Utilisateur findById(Long id) throws Exception {
        String sql = "SELECT * FROM utilisateur WHERE idUser=?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return mapUtilisateur(rs);
        }
        return null;
    }

    // --------------------------------------------
    @Override
    public boolean create(Utilisateur u) throws SQLException {
        String sql = """
            INSERT INTO utilisateur
            (nom,email,adresse,cin,tel,idSexe,login,motdepasse,dateNaissance,lastLoginDate,idRole)
            VALUES (?,?,?,?,?,?,?,?,?,?,?)
        """;
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, u.getNom());
            pr.setString(2, u.getEmail());
            pr.setString(3, u.getAdresse());
            pr.setString(4, u.getCin());
            pr.setString(5, u.getTel());
            pr.setObject(6, u.getIdSexe());
            pr.setString(7, u.getLogin());
            pr.setString(8, u.getMotdepasse());
            pr.setDate(9, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);
            pr.setTimestamp(10, u.getLastLoginDate() != null ? Timestamp.valueOf(u.getLastLoginDate()) : null);
            pr.setObject(11, u.getIdRole());

            return pr.executeUpdate() > 0;
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Utilisateur save(Utilisateur user) {
        return null;
    }

    // --------------------------------------------
    @Override
    public Utilisateur update(Utilisateur u) {
        String sql = """
            UPDATE utilisateur SET nom=?,email=?,adresse=?,cin=?,tel=?,idSexe=?,login=?,motdepasse=?,dateNaissance=?,lastLoginDate=?,idRole=?
            WHERE idUser=?
        """;
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, u.getNom());
            pr.setString(2, u.getEmail());
            pr.setString(3, u.getAdresse());
            pr.setString(4, u.getCin());
            pr.setString(5, u.getTel());
            pr.setObject(6, u.getIdSexe());
            pr.setString(7, u.getLogin());
            pr.setString(8, u.getMotdepasse());
            pr.setDate(9, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);
            pr.setTimestamp(10, u.getLastLoginDate() != null ? Timestamp.valueOf(u.getLastLoginDate()) : null);
            pr.setObject(11, u.getIdRole());
            pr.setInt(12, u.getIdUser());

            pr.executeUpdate();
        }
        catch(Exception ignored) {}
        return u;
    }

    // --------------------------------------------
    @Override
    public boolean delete(Utilisateur u) throws SQLException {
        return deleteById((long) u.getIdUser());
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM utilisateur WHERE idUser=?";
        try (Connection con = DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(), new Connextion_db().getPassword());
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() > 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    // --------------------------------------------
    // Mapping method
    // --------------------------------------------
    private Utilisateur mapUtilisateur(ResultSet rs) throws SQLException {
        return Utilisateur.builder()
                .idUser(rs.getInt("idUser"))
                .nom(rs.getString("nom"))
                .email(rs.getString("email"))
                .adresse(rs.getString("adresse"))
                .cin(rs.getString("cin"))
                .tel(rs.getString("tel"))
                .idSexe(rs.getObject("idSexe") != null ? rs.getInt("idSexe") : null)
                .login(rs.getString("login"))
                .motdepasse(rs.getString("motdepasse"))
                .dateNaissance(rs.getDate("dateNaissance") != null ?
                        rs.getDate("dateNaissance").toLocalDate() : null)
                .lastLoginDate(rs.getTimestamp("lastLoginDate") != null ?
                        rs.getTimestamp("lastLoginDate").toLocalDateTime() : null)
                .idRole(rs.getObject("idRole") != null ?
                        rs.getInt("idRole") : null)
                .build();
    }
}
