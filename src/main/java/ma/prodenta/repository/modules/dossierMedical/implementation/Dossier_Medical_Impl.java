package ma.prodenta.repository.modules.dossierMedical.implementation;
import com.mysql.cj.Session;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.modules.dossierMedical.api.DossierMedicalRepository;
import ma.prodenta.config.SessionFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class Dossier_Medical_Impl implements DossierMedicalRepository {

    @Override
    public DossierMedical findById(Integer id) throws Exception {
        String sql = "SELECT * FROM dossiermedical WHERE iddossier = ?";
        DossierMedical dossierMedical=new DossierMedical();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    dossierMedical.setIdDossier(rs.getInt("iddossier"));
                    dossierMedical.setDateCreation(rs.getDate("dateCreation").toLocalDate());
                    dossierMedical.setIdPatient(rs.getInt("idPatient"));
                    dossierMedical.setIdMedecin(rs.getInt("idMedecin"));
                    return dossierMedical;
                }
        }
        return null;
    }

    @Override
    public List<DossierMedical> findAll() throws Exception {

        String sql = "SELECT * FROM dossier_medical";
        List<DossierMedical> result = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }

        return result;
    }

    @Override
    public DossierMedical save(DossierMedical dossier) throws Exception {
        String sql = """
            INSERT INTO dossiermedical
            (datecreation, idpatient, idmedecin)
            VALUES ( ?, ?, ?)
        """;
        DossierMedical dossier1=new DossierMedical();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(dossier.getDateCreation()));
            ps.setInt(2, dossier.getIdPatient());
            ps.setInt(3, dossier.getIdMedecin());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    dossier1=findById(keys.getInt(1));
                }
            }
        }
        return dossier1;
    }

    @Override
    public DossierMedical update(DossierMedical dossier) throws Exception {

        String sql = """
            UPDATE dossier_medical SET
            date_creation = ?, id_patient = ?, id_medecin = ?,
            allergies = ?, antecedents = ?, notes = ?
            WHERE id_dossier = ?
        """;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(dossier.getDateCreation()));
            ps.setInt(2, dossier.getIdPatient());
            ps.setInt(3, dossier.getIdMedecin());
            ps.setString(4, dossier.getAllergies());
            ps.setString(5, dossier.getAntecedents());
            ps.setString(6, dossier.getNotes());
            ps.setInt(7, dossier.getIdDossier());

            ps.executeUpdate();
        }

        return dossier;
    }

    @Override
    public void delete(Integer id) throws Exception {

        String sql = "DELETE FROM dossier_medical WHERE id_dossier = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    private DossierMedical mapRow(ResultSet rs) throws SQLException {
        DossierMedical dossier = new DossierMedical();
        dossier.setIdDossier(rs.getInt("idDossier"));
        dossier.setDateCreation(rs.getDate("dateCreation").toLocalDate());
        dossier.setIdPatient(rs.getInt("idPatient"));
        dossier.setIdMedecin(rs.getInt("idMedecin"));
        return dossier;
    }

    public DossierMedical find_patient(Patient patient) {
        String requete = """
                select * from dossiermedical where idPatient = ?;
                """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(requete);) {
            ps.setInt(1, patient.getId());
            ResultSet rs = ps.executeQuery();
            DossierMedical dossier = new DossierMedical();
            if (rs.next()) {
                dossier.setIdPatient(rs.getInt("idPatient"));
                dossier.setDateCreation(rs.getDate("dateCreation").toLocalDate());
                dossier.setIdMedecin(rs.getInt("idMedecin"));
                dossier.setIdDossier(rs.getInt("idDossier"));
                return dossier;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int get_last_id() throws SQLException {
        String  requete= """
                select count(iddossier) from dossiermedical
        """;
        int id=0;
        try(Connection conn= SessionFactory.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(requete);)
        {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id=rs.getInt(1);
            }
            return id;
        }
    }
}
