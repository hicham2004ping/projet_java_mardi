package ma.prodenta.repository.modules.CabinetMedical.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.CabinetMedical;
import ma.prodenta.repository.modules.CabinetMedical.api.CabinetMedicaleDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CabinetMedicaleImpl implements CabinetMedicaleDao {

    // ---------------------------
    // FIND ALL
    // ---------------------------
    @Override
    public List<CabinetMedical> findAll() throws Exception {
        List<CabinetMedical> list = new ArrayList<>();
        String sql = "SELECT * FROM cabinetmedical";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToCabinet(rs));
            }
        }
        return list;
    }

    // ---------------------------
    // FIND BY ID
    // ---------------------------
    @Override
    public CabinetMedical findById(Integer id) throws Exception {
        String sql = "SELECT * FROM cabinetmedical WHERE idCabinet = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapToCabinet(rs);
            }
        }
        return null;
    }

    // ---------------------------
    // CREATE
    // ---------------------------
    @Override
    public boolean create(CabinetMedical cab) throws SQLException, IOException {
        String sql = """
            INSERT INTO cabinetmedical 
            (nom, email, logo, adresse, tel1, tel2, siteweb, description, instagram, facebook)
            VALUES (?,?,?,?,?,?,?,?,?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cab.getNom());
            ps.setString(2, cab.getEmail());
            ps.setString(3, cab.getLogo());
            ps.setString(4, cab.getAdresse());
            ps.setString(5, cab.getTel1());
            ps.setString(6, cab.getTel2());
            ps.setString(7, cab.getSiteweb());
            ps.setString(8, cab.getDescription());
            ps.setString(9, cab.getInstagram());
            ps.setString(10, cab.getFacebook());

            int s = ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) cab.setIdCabinet(keys.getInt(1));
            }

            return s > 0;
        }
    }

    // ---------------------------
    // UPDATE
    // ---------------------------
    @Override
    public void update(CabinetMedical cab) throws Exception {
        String sql = """
            UPDATE cabinetmedical SET 
                nom=?, email=?, logo=?, adresse=?, tel1=?, tel2=?, 
                siteweb=?, description=?, instagram=?, facebook=?
            WHERE idCabinet=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cab.getNom());
            ps.setString(2, cab.getEmail());
            ps.setString(3, cab.getLogo());
            ps.setString(4, cab.getAdresse());
            ps.setString(5, cab.getTel1());
            ps.setString(6, cab.getTel2());
            ps.setString(7, cab.getSiteweb());
            ps.setString(8, cab.getDescription());
            ps.setString(9, cab.getInstagram());
            ps.setString(10, cab.getFacebook());
            ps.setInt(11, cab.getIdCabinet());

            ps.executeUpdate();
        }
    }

    // ---------------------------
    // DELETE
    // ---------------------------
    @Override
    public boolean delete(CabinetMedical cab) throws Exception {
        return deleteById(cab.getIdCabinet());
    }

    @Override
    public boolean deleteById(Integer id) throws Exception {
        String sql = "DELETE FROM cabinetmedical WHERE idCabinet = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------------------------
    // findByNom du CrudRepository (NON UTILISÉ)
    // ---------------------------
    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    // ---------------------------
    // Convert ResultSet → CabinetMedical
    // ---------------------------
    private CabinetMedical mapToCabinet(ResultSet rs) throws SQLException {
        return CabinetMedical.builder()
                .idCabinet(rs.getInt("idCabinet"))
                .nom(rs.getString("nom"))
                .email(rs.getString("email"))
                .logo(rs.getString("logo"))
                .adresse(rs.getString("adresse"))
                .tel1(rs.getString("tel1"))
                .tel2(rs.getString("tel2"))
                .siteweb(rs.getString("siteweb"))
                .description(rs.getString("description"))
                .instagram(rs.getString("instagram"))
                .facebook(rs.getString("facebook"))
                .build();
    }

    // ---------------------------
    // Obtenir dernier ID
    // ---------------------------
    public int get_last_id() {
        int lastId = 0;
        String sql = "SELECT MAX(idCabinet) AS last_id FROM cabinetmedical";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) lastId = rs.getInt("last_id");

        } catch (SQLException e) {
            System.out.println("Erreur get_last_id CabinetMedical : " + e.getMessage());
        }

        return lastId;
    }
}
