package ma.prodenta.repository.modules.statistiques.fileBase_implementation;

import ma.prodenta.config.DatabaseConnection;
import ma.prodenta.entities.En.Revenus;
import ma.prodenta.repository.modules.revenus.api.RevenusDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevenusDAOImpl implements RevenusDao {

    @Override
    public void create(Revenus revenu) throws Exception {
        String sql = "INSERT INTO revenus (type, description, montant, dateRev, idCabinet) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, revenu.getType());
            stmt.setString(2, revenu.getDescription());
            stmt.setDouble(3, revenu.getMontant());
            stmt.setTimestamp(4, new Timestamp(revenu.getDateRev().getTime()));
            stmt.setInt(5, revenu.getIdCabinet());

            stmt.executeUpdate();
        }
    }

    @Override
    public Revenus findById(Integer id) throws Exception {
        String sql = "SELECT * FROM revenus WHERE idRev = ?";
        Revenus revenu = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                revenu = mapResultSetToRevenu(rs);
            }
        }
        return revenu;
    }

    @Override
    public List<Revenus> findAll() throws Exception {
        List<Revenus> list = new ArrayList<>();
        String sql = "SELECT * FROM revenus";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToRevenu(rs));
            }
        }
        return list;
    }

    @Override
    public void update(Revenus revenu) throws Exception {
        String sql = "UPDATE revenus SET type = ?, description = ?, montant = ?, dateRev = ?, idCabinet = ? WHERE idRev = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, revenu.getType());
            stmt.setString(2, revenu.getDescription());
            stmt.setDouble(3, revenu.getMontant());
            stmt.setTimestamp(4, new Timestamp(revenu.getDateRev().getTime()));
            stmt.setInt(5, revenu.getIdCabinet());
            stmt.setInt(6, revenu.getIdRev());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM revenus WHERE idRev = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Revenus mapResultSetToRevenu(ResultSet rs) throws SQLException {
        return Revenus.builder()
                .idRev(rs.getInt("idRev"))
                .type(rs.getString("type"))
                .description(rs.getString("description"))
                .montant(rs.getDouble("montant"))
                .dateRev(rs.getTimestamp("dateRev"))
                .idCabinet(rs.getInt("idCabinet"))
                .build();
    }
}
