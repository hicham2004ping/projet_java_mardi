package ma.prodenta.repository.modules.dossierMedical;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.repository.common.DossierMedicalRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
                            JDBC.
 */
public class DossierMedicalRepositoryImpl implements DossierMedicalRepository {

    private final SessionFactory sessionFactory;

    public DossierMedicalRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<DossierMedical> findById(Long id) {
        String sql = "SELECT * FROM DossierMedical WHERE id = ?";

        try (Connection connection = sessionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToDossier(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<DossierMedical> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM DossierMedical WHERE patient_id = ?";
        List<DossierMedical> result = new ArrayList<>();

        try (Connection connection = sessionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRowToDossier(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public DossierMedical save(DossierMedical dossier) {
        String sql = "INSERT INTO DossierMedical (patient_id, allergies, antecedents, notes, date_creation) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = sessionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, dossier.getPatientId());
            ps.setString(2, dossier.getAllergies());
            ps.setString(3, dossier.getAntecedents());
            ps.setString(4, dossier.getNotes());
            ps.setDate(5, Date.valueOf(
                    (String) (dossier.getDateCreation() != null ? dossier.getDateCreation() : LocalDate.now())
            ));

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    dossier.setIdDossier(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dossier;
    }

    @Override
    public DossierMedical update(DossierMedical dossier) {
        String sql = "UPDATE DossierMedical SET allergies = ?, antecedents = ?, notes = ? WHERE id = ?";

        try (Connection connection = sessionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, dossier.getAllergies());
            ps.setString(2, dossier.getAntecedents());
            ps.setString(3, dossier.getNotes());
            ps.setLong(4, dossier.getIdDossier());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dossier;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM DossierMedical WHERE id = ?";

        try (Connection connection = sessionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DossierMedical mapRowToDossier(ResultSet rs) throws SQLException {
        DossierMedical dossier = new DossierMedical();
        dossier.setIdDossier(rs.getLong("id"));
        dossier.setPatientId(rs.getLong("patient_id"));
        dossier.setAllergies(rs.getString("allergies"));
        dossier.setAntecedents(rs.getString("antecedents"));
        dossier.setNotes(rs.getString("notes"));
        Date dateCreation = rs.getDate("date_creation");
        if (dateCreation != null) {
            return null;
        }
        return dossier;
    }
}
