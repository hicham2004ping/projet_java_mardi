package ma.prodenta.repository.modules.Facture.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Facture;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.Facture.api.FactureDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactureDaoimpl implements FactureDao {

    @Override
    public List<Facture> findAll() throws Exception {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Facture";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToFacture(rs));
            }
        }
        return list;
    }

    @Override
    public Facture findById(Integer idFact) throws Exception {
        String sql = "SELECT * FROM Facture WHERE idFact = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFact);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFacture(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean create(Facture facture) throws SQLException {
        String sql = "INSERT INTO Facture (total, totalpaye, reste, statut, dateFact, idSF) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, facture.getTotal());
            stmt.setDouble(2, facture.getTotalpaye());
            stmt.setDouble(3, facture.getReste());
            stmt.setString(4, facture.getStatut());
            stmt.setDate(5, new java.sql.Date(facture.getDateFact().getTime()));
            stmt.setInt(6, facture.getIdSF());

            int n = stmt.executeUpdate();
            return n > 0;
        }
    }

    @Override
    public Utilisateur update(Facture facture) throws SQLException {
        String sql = "UPDATE Facture SET total = ?, totalpaye = ?, reste = ?, statut = ?, dateFact = ?, idSF = ? WHERE idFact = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, facture.getTotal());
            stmt.setDouble(2, facture.getTotalpaye());
            stmt.setDouble(3, facture.getReste());
            stmt.setString(4, facture.getStatut());
            stmt.setDate(5, new java.sql.Date(facture.getDateFact().getTime()));
            stmt.setInt(6, facture.getIdSF());
            stmt.setInt(7, facture.getIdFact());

            stmt.executeUpdate();
        }
        return null;
    }

    @Override
    public boolean delete(Facture facture) throws SQLException {
        String sql = "DELETE FROM Facture WHERE idFact = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, facture.getIdFact());
            int n = stmt.executeUpdate();
            return n > 0;
        }
    }

    @Override
    public boolean deleteById(Integer idFact) throws SQLException {
        String sql = "DELETE FROM Facture WHERE idFact = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFact);
            int n = stmt.executeUpdate();
            return n > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    private Facture mapResultSetToFacture(ResultSet rs) throws SQLException {
        return Facture.builder()
                .idFact(rs.getInt("idFact"))
                .total(rs.getDouble("total"))
                .totalpaye(rs.getDouble("totalpaye"))
                .reste(rs.getDouble("reste"))
                .statut(rs.getString("statut"))
                .dateFact(rs.getDate("dateFact"))
                .idSF(rs.getInt("idSF"))
                .build();
    }
}
