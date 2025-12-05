// repository/modules/DossierMedical/implementation/DossierMedicalRepositoryImpl.java
package ma.prodenta.repository.modules.dossierMedical.implementation;

import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.repository.modules.dossierMedical.api.DossierMedicalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DossierMedicalRepositoryImpl implements DossierMedicalRepository {

    private final Connection connection;

    public DossierMedicalRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public DossierMedical findById(Integer id) throws Exception {
        String sql = "SELECT * FROM dossier_medical WHERE id_dossier = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<DossierMedical> findAll() throws Exception {
        String sql = "SELECT * FROM dossier_medical";
        List<DossierMedical> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }
        return result;
    }

    @Override
    public DossierMedical save(DossierMedical dossier) throws Exception {
        String sql = "INSERT INTO dossier_medical " +
                "(date_creation, id_patient, id_medecin, allergies, antecedents, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, new java.sql.Date(dossier.getDateCreation().getTime()));
            ps.setInt(2, dossier.getIdPatient());
            ps.setInt(3, dossier.getIdMedecin());
            ps.setString(4, dossier.getAllergies());
            ps.setString(5, dossier.getAntecedents());
            ps.setString(6, dossier.getNotes());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    dossier.setIdDossier(keys.getInt(1));
                }
            }
        }
        return dossier;
    }

    @Override
    public DossierMedical update(DossierMedical dossier) throws Exception {
        String sql = "UPDATE dossier_medical SET " +
                "date_creation = ?, id_patient = ?, id_medecin = ?, " +
                "allergies = ?, antecedents = ?, notes = ? " +
                "WHERE id_dossier = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(dossier.getDateCreation().getTime()));
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
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // mappe une ligne vers l'entité
    private DossierMedical mapRow(ResultSet rs) throws SQLException {
        DossierMedical dossier = new DossierMedical();
        dossier.setIdDossier(rs.getInt("id_dossier"));
        dossier.setDateCreation(rs.getDate("date_creation"));
        dossier.setIdPatient(rs.getInt("id_patient"));
        dossier.setIdMedecin(rs.getInt("id_medecin"));
        dossier.setAllergies(rs.getString("allergies"));
        dossier.setAntecedents(rs.getString("antecedents"));
        dossier.setNotes(rs.getString("notes"));
        return dossier;
    }
}
