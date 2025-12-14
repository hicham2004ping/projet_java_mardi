package ma.prodenta.repository.modules.staff.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Staff;
import ma.prodenta.repository.modules.staff.api.StaffDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffDaoImpl implements StaffDao {

    @Override
    public List<Staff> findAll() throws Exception {
        List<Staff> staffs = new ArrayList<>();
        String sql = "SELECT * FROM staff";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Staff staff = mapToStaff(rs);
                staffs.add(staff);
            }
        }

        return staffs;
    }

    @Override
    public Staff findById(Integer id) throws Exception {
        String sql = "SELECT * FROM staff WHERE idStaff = ?";
        Staff staff = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                staff = mapToStaff(rs);
            }
        }

        return staff;
    }

    @Override
    public boolean create(Staff staff) throws SQLException, IOException {
        String sql = "INSERT INTO staff (idStaff, salaire, prime, dateRecrutement, soldeConge, idMedecin, idSecretaire) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, staff.getIdStaff());
            pst.setDouble(2, staff.getSalaire());
            pst.setDouble(3, staff.getPrime());
            pst.setDate(4, new java.sql.Date(staff.getDateRecrutement().getTime()));
            pst.setInt(5, staff.getSoldeConge());
            pst.setInt(6, staff.getIdMedecin());
            pst.setInt(7, staff.getIdSecretaire());

            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public void update(Staff staff) throws SQLException, IOException, Exception {
        String sql = "UPDATE staff SET salaire = ?, prime = ?, dateRecrutement = ?, soldeConge = ?, idMedecin = ?, idSecretaire = ? " +
                "WHERE idStaff = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setDouble(1, staff.getSalaire());
            pst.setDouble(2, staff.getPrime());
            pst.setDate(3, new java.sql.Date(staff.getDateRecrutement().getTime()));
            pst.setInt(4, staff.getSoldeConge());
            pst.setInt(5, staff.getIdMedecin());
            pst.setInt(6, staff.getIdSecretaire());
            pst.setInt(7, staff.getIdStaff());

            pst.executeUpdate();
        }
    }

    @Override
    public boolean delete(Staff staff) throws SQLException, Exception {
        return deleteById(staff.getIdStaff());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException, Exception {
        String sql = "DELETE FROM staff WHERE idStaff = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty(); // pas applicable pour Staff
    }

    /**
     * Retourne le dernier idStaff dans la table staff
     */
    public Integer get_last_id() {
        String sql = "SELECT MAX(idStaff) AS last_id FROM staff";
        Integer lastId = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("last_id");
                if (rs.wasNull()) {
                    lastId = null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lastId;
    }

    /**
     * Transforme un ResultSet en objet Staff
     */
    public Staff mapToStaff(ResultSet rs) throws SQLException {
        if (rs == null) return null;

        return Staff.builder()
                .idStaff(rs.getInt("idStaff"))
                .salaire(rs.getDouble("salaire"))
                .prime(rs.getDouble("prime"))
                .dateRecrutement(rs.getDate("dateRecrutement"))
                .soldeConge(rs.getInt("soldeConge"))
                .idMedecin(rs.getInt("idMedecin"))
                .idSecretaire(rs.getInt("idSecretaire"))
                .build();
    }
}
