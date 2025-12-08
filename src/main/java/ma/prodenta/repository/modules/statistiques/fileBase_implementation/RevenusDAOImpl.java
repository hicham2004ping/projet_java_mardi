package ma.prodenta.repository.modules.statistiques.fileBase_implementation;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Revenus;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.statistiques.api.RevenusDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevenusDAOImpl implements RevenusDao {

    @Override
    public Revenus findById(Long idRevenue) throws Exception {
        String sql = "SELECT * FROM revenus WHERE idRev = ?";
        Revenus revenu = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idRevenue);
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

        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToRevenu(rs));
            }
        }
        return list;
    }

    @Override
    public boolean create(Revenus revenu) throws SQLException {
        int nombre=0;
        String sql = "INSERT INTO revenus (type, description, montant, dateRev, idCabinet) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, revenu.getType());
            stmt.setString(2, revenu.getDescription());
            stmt.setDouble(3, revenu.getMontant());
            stmt.setTimestamp(4, new Timestamp(revenu.getDateRev().getTime()));
            stmt.setInt(5, revenu.getIdCabinet());

           nombre= stmt.executeUpdate();

            // récupérer l'ID généré automatiquement
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    revenu.setIdRev(rs.getLong(1));
                }
            }
        }
        return nombre>0;
    }

    @Override
    public void update(Revenus revenu) throws SQLException, Exception , IOException {
        String sql = "UPDATE revenus SET type = ?, description = ?, montant = ?, dateRev = ?, idCabinet = ? WHERE idRev = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, revenu.getType());
            stmt.setString(2, revenu.getDescription());
            stmt.setDouble(3, revenu.getMontant());
            stmt.setTimestamp(4, new Timestamp(revenu.getDateRev().getTime()));
            stmt.setInt(5, revenu.getIdCabinet());
            stmt.setLong(6, revenu.getIdRev());

            stmt.executeUpdate();
        }
    }

    @Override
    public boolean delete(Revenus revenu) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Long idRevenue) throws SQLException {
        String sql = "DELETE FROM revenus WHERE idRev = ?";
        int nombre=0;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idRevenue);
           nombre= stmt.executeUpdate();
        }
        return nombre>0 ;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    // mapping ResultSet → Revenus
    private Revenus mapResultSetToRevenu(ResultSet rs) throws SQLException {
        return Revenus.builder()
                .idRev(rs.getLong("idRev"))
                .type(rs.getString("type"))
                .description(rs.getString("description"))
                .montant(rs.getDouble("montant"))
                .dateRev(rs.getTimestamp("dateRev"))
                .idCabinet(rs.getInt("idCabinet"))
                .build();
    }
    public Long get_last_id() {
        Long lastId = 00000L;
        String sql = "SELECT MAX(idRev) AS last_id FROM Revenus"; // ou le nom exact de ta table

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
