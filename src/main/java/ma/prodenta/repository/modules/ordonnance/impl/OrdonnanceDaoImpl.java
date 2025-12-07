package ma.prodenta.repository.modules.ordonnance.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.repository.modules.ordonnance.api.Ordonance_api;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceDaoImpl implements Ordonance_api {

    @Override
    public Ordonnance findById(Long idOrd) throws Exception {
        String sql = "SELECT * FROM ordonnance WHERE idOrd = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idOrd);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ordonnance(
                            rs.getLong("idOrd"),
                            rs.getDate("dateOrd"),
                            rs.getInt("idDossier")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Ordonnance> findAll() throws Exception {
        List<Ordonnance> liste = new ArrayList<>();
        String sql = "SELECT * FROM ordonnance";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                liste.add(new Ordonnance(
                        rs.getLong("idOrd"),
                        rs.getDate("dateOrd"),
                        rs.getInt("idDossier")
                ));
            }
        }
        return liste;
    }

    @Override
    public boolean create(Ordonnance ord) throws SQLException {
        int n = 0;
        String sql = "INSERT INTO Ordonnance (dateOrd, idDossier) VALUES (?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(ord.getDateOrd().getTime()));
            stmt.setInt(2, ord.getIdDossier());

            n = stmt.executeUpdate();
        }

        return n > 0;
    }

    @Override
    public void update(Ordonnance ord) throws Exception {
        String sql = "UPDATE Ordonnance SET dateOrd = ?, idDossier = ? WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(ord.getDateOrd().getTime()));
            stmt.setInt(2, ord.getIdDossier());
            stmt.setLong(3, ord.getIdOrd());

            stmt.executeUpdate();
        }
    }

    @Override
    public boolean delete(Ordonnance ord) throws Exception {
        int n = 0;
        String sql = "DELETE FROM Ordonnance WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ord.getIdOrd());
            n = stmt.executeUpdate();
        }

        return n > 0;
    }

    @Override
    public boolean deleteById(Long idOrd) throws Exception {
        int n = 0;
        String sql = "DELETE FROM Ordonnance WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idOrd);
            n = stmt.executeUpdate();
        }

        return n > 0;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    @Override
    public List<Medicament> find_all_medicament_in_ordonance(Ordonnance ordonance) {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = """
            SELECT m.* 
            FROM medicament m
            JOIN ordonnance_medicament om ON om.idMed = m.idMed
            WHERE om.idOrd = ?
        """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ordonance.getIdOrd());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medicaments.add(new Medicament(
                            rs.getInt("idMed"),
                            rs.getString("nom"),
                            rs.getString("laboratoire"),
                            rs.getString("type"),
                            rs.getBoolean("remboursable"),
                            rs.getDouble("prixUnit"),
                            rs.getString("description"),
                            rs.getInt("idForme")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicaments;
    }

    @Override
    public int Total_ordonance(Ordonnance ordonance) {
        String sql = "SELECT SUM(m.prixUnit) " +
                "FROM medicament m " +
                "JOIN ordonnance_medicament om ON om.idMed = m.idMed " +
                "WHERE om.idOrd = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ordonance.getIdOrd());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // total des prix
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
