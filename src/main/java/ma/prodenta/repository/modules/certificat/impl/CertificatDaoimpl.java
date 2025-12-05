package ma.prodenta.repository.modules.certificat.impl;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Certificat;
import ma.prodenta.repository.modules.certificat.api.CertificatDao;
import ma.prodenta.config.SessionFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificatDaoimpl implements CertificatDao {

    @Override
    public List<Certificat> findAll() throws Exception {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM Certificat";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(resultToCertificat(rs));
            }
        }
        return list;
    }

    @Override
    public Certificat findById(Integer id) throws Exception {
        String sql = "SELECT * FROM Certificat WHERE idCert = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return resultToCertificat(rs);
            }
        }
        return null;
    }

    @Override
    public boolean create(Certificat cert) throws SQLException, IOException {
        String sql = """
            INSERT INTO Certificat(dateDebut, dateFin, nature, noteMedecin, idDossier)
            VALUES(?,?,?,?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, new java.sql.Date(cert.getDateDebut().getTime()));
            ps.setDate(2, new java.sql.Date(cert.getDateFin().getTime()));
            ps.setString(3, cert.getNature());
            ps.setString(4, cert.getNoteMedecin());
            ps.setInt(5, cert.getIdDossier());

            int s = ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) cert.setIdCert(keys.getInt(1));
            }

            return s > 0;
        }
    }

    @Override
    public void update(Certificat cert) throws Exception {
        String sql = """
            UPDATE Certificat
            SET dateDebut=?, dateFin=?, nature=?, noteMedecin=?, idDossier=?
            WHERE idCert=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(cert.getDateDebut().getTime()));
            ps.setDate(2, new java.sql.Date(cert.getDateFin().getTime()));
            ps.setString(3, cert.getNature());
            ps.setString(4, cert.getNoteMedecin());
            ps.setInt(5, cert.getIdDossier());
            ps.setInt(6, cert.getIdCert());

            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(Certificat cert) throws Exception {
        return deleteById(cert.getIdCert());
    }

    @Override
    public boolean deleteById(Integer id) throws Exception {
        String sql = "DELETE FROM Certificat WHERE idCert = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty(); // NON UTILISÉ pour Certificat
    }

    // Convertisseur ResultSet → Certificat
    private Certificat resultToCertificat(ResultSet rs) throws SQLException {
        return Certificat.builder()
                .idCert(rs.getInt("idCert"))
                .dateDebut(rs.getDate("dateDebut"))
                .dateFin(rs.getDate("dateFin"))
                .nature(rs.getString("nature"))
                .noteMedecin(rs.getString("noteMedecin"))
                .idDossier(rs.getInt("idDossier"))
                .build();
    }

    static void main(String[] args) {

    }
}
