package ma.dentalTech.repository.modules.patient.fileBase_implementation;

import ma.dentalTech.repository.modules.patient.api.PatientDao;
import ma.dentalTech.entities.En.Patient;
import ma.dentalTech.conf.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDao {

    @Override
    public void ajouter(Patient patient) throws Exception {
        String sql = "INSERT INTO patient(nom, dateNaissance, adresse, telephone, idSexe, idAssurance) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getDateNaissance().toString());
            ps.setString(3, patient.getAdresse());
            ps.setString(4, patient.getTelephone());
            ps.setInt(5, patient.getIdSexe());
            ps.setInt(6, patient.getIdAssurance());
            ps.executeUpdate();
        }
    }

    @Override
    public void mettreAJour(Patient patient) throws Exception {
        String sql = "UPDATE patient SET nom=?, dateNaissance=?, adresse=?, telephone=?, idSexe=?, idAssurance=? WHERE idPatient=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getNom());
            ps.setString(2, String.valueOf(patient.getDateNaissance()));
            ps.setString(3, patient.getAdresse());
            ps.setString(4, patient.getTelephone());
            ps.setInt(5, patient.getIdSexe());
            ps.setInt(6, patient.getIdAssurance());
            ps.setInt(7, patient.getIdPatient());
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int idPatient) throws Exception {
        String sql = "DELETE FROM patient WHERE idPatient=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            ps.executeUpdate();
        }
    }

    @Override
    public Patient trouverParId(int idPatient) throws Exception {
        String sql = "SELECT * FROM patient WHERE idPatient=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                            rs.getInt("idPatient"),
                            rs.getString("nom"),
                            rs.getDate("dateNaissance"),
                            rs.getString("adresse"),
                            rs.getString("telephone"),
                            rs.getInt("idSexe"),
                            rs.getInt("idAssurance")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Patient> trouverTous() throws Exception {
        List<Patient> liste = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Patient(
                        rs.getInt("idPatient"),
                        rs.getString("nom"),
                        rs.getDate("dateNaissance"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getInt("idSexe"),
                        rs.getInt("idAssurance")
                ));
            }
        }
        return liste;
    }
}
