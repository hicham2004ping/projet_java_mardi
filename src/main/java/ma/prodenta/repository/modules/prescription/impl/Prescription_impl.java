package ma.prodenta.repository.modules.prescription.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.prescription.api.Prescription_api;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Prescription_impl implements Prescription_api {

    @Override
    public int total_prescriptions(Ordonnance ordonance) {
        String sql = "SELECT COUNT(*) FROM Prescription WHERE idOrd = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ordonance.getIdOrd());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Prescription> findAll() throws Exception {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM Prescription";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToPrescription(rs));
            }
        }
        return list;
    }

    @Override
    public Prescription findById(Integer idPr) throws Exception {
        String sql = "SELECT * FROM Prescription WHERE idPr = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPr);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrescription(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean create(Prescription presc) throws SQLException, IOException {
        String sql = "INSERT INTO Prescription (quantite, frequence, dureeEnJours, idOrd, idMed) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, presc.getQuantite());
            stmt.setString(2, presc.getFrequence());
            stmt.setInt(3, presc.getDureeEnJours());
            stmt.setInt(4, presc.getIdOrd());
            stmt.setInt(5, presc.getIdMed());

            int n = stmt.executeUpdate();
            return n > 0;
        }
    }

    @Override
    public Utilisateur update(Prescription presc) throws SQLException, IOException, Exception {
        String sql = "UPDATE Prescription SET quantite = ?, frequence = ?, dureeEnJours = ?, idOrd = ?, idMed = ? WHERE idPr = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, presc.getQuantite());
            stmt.setString(2, presc.getFrequence());
            stmt.setInt(3, presc.getDureeEnJours());
            stmt.setInt(4, presc.getIdOrd());
            stmt.setInt(5, presc.getIdMed());
            stmt.setInt(6, presc.getIdPr());

            stmt.executeUpdate();
        }
        return null;
    }

    @Override
    public boolean delete(Prescription presc) throws SQLException, Exception {
        String sql = "DELETE FROM Prescription WHERE idPr = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, presc.getIdPr());
            int n = stmt.executeUpdate();
            return n > 0;
        }
    }

    @Override
    public boolean deleteById(Integer idPr) throws SQLException, Exception {
        String sql = "DELETE FROM Prescription WHERE idPr = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPr);
            int n = stmt.executeUpdate();
            return n > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    private Prescription mapResultSetToPrescription(ResultSet rs) throws SQLException {
        return Prescription.builder()
                .idPr(rs.getInt("idPr"))
                .quantite(rs.getInt("quantite"))
                .frequence(rs.getString("frequence"))
                .dureeEnJours(rs.getInt("dureeEnJours"))
                .idOrd(rs.getInt("idOrd"))
                .idMed(rs.getInt("idMed"))
                .build();
    }
}
