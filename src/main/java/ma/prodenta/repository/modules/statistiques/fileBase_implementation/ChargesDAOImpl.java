package ma.prodenta.repository.modules.statistiques.fileBase_implementation;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.modules.statistiques.api.ChargesDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChargesDAOImpl implements ChargesDao {

    @Override
    public void create(Charges charge) throws Exception {
        String sql = "INSERT INTO charges (titre, description, montant, dateCharge, idCabinet) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, charge.getTitre());
            stmt.setString(2, charge.getDescription());
            stmt.setDouble(3, charge.getMontant());
            stmt.setTimestamp(4, new Timestamp(charge.getDateCharge().getTime()));
            stmt.setInt(5, charge.getIdCabinet());

            stmt.executeUpdate();
        }
    }

    @Override
    public Charges findById(Integer id) throws Exception {
        String sql = "SELECT * FROM charges WHERE idCharge = ?";
        Charges charge = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                charge = mapResultSetToCharge(rs);
            }
        }
        return charge;
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
    public void update(Charges charge) throws Exception {
        String sql = "UPDATE charges SET titre = ?, description = ?, montant = ?, dateCharge = ?, idCabinet = ? WHERE idCharge = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, charge.getTitre());
            stmt.setString(2, charge.getDescription());
            stmt.setDouble(3, charge.getMontant());
            stmt.setTimestamp(4, new Timestamp(charge.getDateCharge().getTime()));
            stmt.setInt(5, charge.getIdCabinet());
            stmt.setInt(6, charge.getIdCharge());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM charges WHERE idCharge = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Charges mapResultSetToCharge(ResultSet rs) throws SQLException {
        return Charges.builder()
                .idCharge(rs.getInt("idCharge"))
                .titre(rs.getString("titre"))
                .description(rs.getString("description"))
                .montant(rs.getDouble("montant"))
                .dateCharge(rs.getTimestamp("dateCharge"))
                .idCabinet(rs.getInt("idCabinet"))
                .build();
    }
}
