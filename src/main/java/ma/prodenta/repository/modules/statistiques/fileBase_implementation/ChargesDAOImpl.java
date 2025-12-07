package ma.prodenta.repository.modules.statistiques.fileBase_implementation;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Charges;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.statistiques.api.ChargesDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChargesDAOImpl implements ChargesDao {

    @Override
    public Charges findById(Long aLong) throws Exception {
        String sql = "SELECT * FROM charges WHERE idCharge = ?";
        Charges charge = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, aLong);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                charge = mapResultSetToCharge(rs);
            }
        }
        return charge;
    }

    @Override
    public boolean create(Charges charge) throws SQLException, IOException {
        int n=0;
        String sql = "INSERT INTO charges (titre, description, montant, dateCharge, idCabinet) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, charge.getTitre());
            stmt.setString(2, charge.getDescription());
            stmt.setDouble(3, charge.getMontant());
            stmt.setTimestamp(4, new Timestamp(charge.getDateCharge().getTime()));
            stmt.setInt(5, charge.getIdCabinet());

           n= stmt.executeUpdate();

            // récupérer l'ID généré
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    charge.setIdCharge(rs.getLong(1));
                }
            }
        }
        return n>0;
    }

    @Override
    public List<Charges> findAll() throws Exception {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM charges";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToCharge(rs));
            }
        }
        return list;
    }

    @Override
    public Utilisateur update(Charges charge) throws Exception {
        String sql = "UPDATE charges SET titre = ?, description = ?, montant = ?, dateCharge = ?, idCabinet = ? WHERE idCharge = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, charge.getTitre());
            stmt.setString(2, charge.getDescription());
            stmt.setDouble(3, charge.getMontant());
            stmt.setTimestamp(4, new Timestamp(charge.getDateCharge().getTime()));
            stmt.setInt(5, charge.getIdCabinet());
            stmt.setLong(6, charge.getIdCharge());

            stmt.executeUpdate();
        }
        return null;
    }

    @Override
    public boolean delete(Charges objet) {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) throws SQLException {
        int n=0;
        String sql = "DELETE FROM charges WHERE idCharge = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, aLong);
           n= stmt.executeUpdate();
        }
        return n>0;
    }
    // méthode pour mapper ResultSet → Charges
    private Charges mapResultSetToCharge(ResultSet rs) throws SQLException {
        return Charges.builder()
                .idCharge(rs.getLong("idCharge"))
                .titre(rs.getString("titre"))
                .description(rs.getString("description"))
                .montant(rs.getDouble("montant"))
                .dateCharge(rs.getTimestamp("dateCharge"))
                .idCabinet(rs.getInt("idCabinet"))
                .build();
    }
    public Long get_last_id() {
        Long lastId = 00000L;
        String sql = "SELECT MAX(idCharge) AS last_id FROM Charges"; // ou le nom exact de ta table

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getLong("last_id");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du dernier ID : " + e.getMessage());
        }

        return lastId;
    }
}

