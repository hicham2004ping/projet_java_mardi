package ma.prodenta.repository.modules.medicament.fileBase_implementation;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.repository.modules.medicament.api.MedicamentDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.repository.modules.medicament.api.MedicamentDao;
public class MedicamentDAOImpl implements MedicamentDao {

    public int last_id() throws SQLException, ClassNotFoundException {
        String requete= """
                select max(idMed) from medicament
                """;
        Medicament medicament = new Medicament();
        int id = 0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement stmt=conn.prepareStatement(requete);)
        {
                ResultSet rs=stmt.executeQuery();
                if(rs.next()){
                    id=rs.getInt(1);
                }
                return id;
        }
    }

    @Override
    public Medicament findById(Integer idMed) throws Exception {
        String sql = "SELECT * FROM medicament WHERE idMed = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idMed);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedicament(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Medicament> findAll() throws Exception {
        List<Medicament> liste = new ArrayList<>();
        String sql = "SELECT * FROM medicament";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(mapResultSetToMedicament(rs));
            }
        }
        return liste;
    }

    @Override
    public boolean create(Medicament medicament) throws SQLException {
        int n=0;
        String sql = "INSERT INTO medicament (nom, laboratoire, type, remboursable, prixUnit, description, idForme) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, medicament.getNom());
            stmt.setString(2, medicament.getLaboratoire());
            stmt.setString(3, medicament.getType());
            stmt.setBoolean(4, medicament.getRemboursable());
            stmt.setDouble(5, medicament.getPrixUnit());
            stmt.setString(6, medicament.getDescription());
            stmt.setInt(7, medicament.getIdForme());

           n= stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    medicament.setIdMed(rs.getInt(1));
                }
            }
        }
        return n>0;
    }

    @Override
    public void update(Medicament medicament) throws Exception {
        String sql = "UPDATE medicament SET nom = ?, laboratoire = ?, type = ?, remboursable = ?, " +
                "prixUnit = ?, description = ?, idForme = ? WHERE idMed = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medicament.getNom());
            stmt.setString(2, medicament.getLaboratoire());
            stmt.setString(3, medicament.getType());
            stmt.setBoolean(4, medicament.getRemboursable());
            stmt.setDouble(5, medicament.getPrixUnit());
            stmt.setString(6, medicament.getDescription());
            stmt.setInt(7, medicament.getIdForme());
            stmt.setLong(8, medicament.getIdMed());

            stmt.executeUpdate();
        }
    }

    @Override
    public boolean delete(Medicament medicament) throws SQLException,Exception {
       return deleteById(medicament.getIdMed());
    }


    @Override
    public boolean deleteById(Integer idMed) throws Exception {
        String sql = "DELETE FROM medicament WHERE idMed = ?";
        int n;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idMed);
           n= stmt.executeUpdate();
        }
        return n>0;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    private Medicament mapResultSetToMedicament(ResultSet rs) throws SQLException {
        return new Medicament(
                rs.getInt("idMed"),
                rs.getString("nom"),
                rs.getString("laboratoire"),
                rs.getString("type"),
                rs.getBoolean("remboursable"),
                rs.getDouble("prixUnit"),
                rs.getString("description"),
                rs.getInt("idForme")
        );
    }
}
