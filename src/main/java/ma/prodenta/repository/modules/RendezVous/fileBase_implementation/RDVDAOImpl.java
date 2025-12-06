package ma.prodenta.repository.modules.RendezVous.fileBase_implementation;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.modules.RendezVous.api.RDVDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RDVDAOImpl implements RDVDAO {
    @Override public Integer count() {
        String sql = "SELECT COUNT(*) FROM RDV";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override
    public List<RDV> findAll() throws Exception {

        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToRDV(rs));
            }
        }

        return list;
    }

    public RDV findById(Integer id) throws Exception {

        String sql = "SELECT * FROM RDV WHERE idRDV = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRDV(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<RDV> FindByDay(Date date) throws Exception {

        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV WHERE DATE(dateRDV) = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(date.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToRDV(rs));
                }
            }
        }

        return list;
    }

    @Override public boolean existsById(Integer id) {
        String sql = "SELECT 1 FROM RDV WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    private RDV mapResultSetToRDV(ResultSet rs) throws SQLException {
        return (RDV) RDV.builder()
                .idRDV(rs.getInt("idRDV"))
                .dateRDV(rs.getDate("dateRDV"))
                .heure(rs.getTime("heure"))
                .motif(rs.getString("motif"))
                .noteMedecin(rs.getString("noteMedecin"))
                .idPatient(rs.getInt("idPatient"))
                .build();
    }


    @Override
    public boolean create(RDV rdv){

        String sql = "INSERT INTO RDV (dateRDV, heure, motif, noteMedecin, idPatient) " +
                "VALUES (?, ?, ?, ?, ?)";
        int nombre =0;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(rdv.getDateRDV().getTime()));
            stmt.setTime(2, rdv.getHeure());
            stmt.setString(3, rdv.getMotif());
            stmt.setString(4, rdv.getNoteMedecin());
            stmt.setLong(5, rdv.getIdPatient());
           nombre = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nombre>0;
    }


    @Override
    public void update(RDV rdv) {

        String sql = "UPDATE RDV SET dateRDV = ?, heure = ?, motif = ?, noteMedecin = ?, idPatient = ? " +
                "WHERE idRDV = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(rdv.getDateRDV().getTime()));
            stmt.setTime(2, rdv.getHeure());
            stmt.setString(3, rdv.getMotif());
            stmt.setString(4, rdv.getNoteMedecin());
            stmt.setLong(5, rdv.getIdPatient());
            stmt.setLong(6, rdv.getIdRDV());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(RDV rdv) throws SQLException{
        String sql = "DELETE FROM RDV WHERE idRDV = ?";
        int nombre=0 ;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, rdv.getIdRDV());
           nombre=stmt.executeUpdate();
        }
        return nombre >0;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
    public int get_last_id() {
        int lastId = 0;
        String sql = "SELECT MAX(idRDV) AS last_id FROM RDV"; // ou le nom exact de ta table

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du dernier ID : " + e.getMessage());
        }

        return lastId;
    }
}


