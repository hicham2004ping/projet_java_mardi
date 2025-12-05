package ma.prodenta.repository.modules.consultation.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.repository.modules.consultation.api.ConsultationDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationDaoimpl implements ConsultationDao {

    private Consultation resultToConsultation(ResultSet rs) throws SQLException {
        return Consultation.builder()
                .idConsult(rs.getInt("idConsult"))
                .dateConsult(rs.getDate("dateConsult")) // colonne DATE
                .observationMedecin(rs.getString("observationMedecin"))
                .idDossier(rs.getInt("idDossier"))
                .idStatut(rs.getInt("idStatut"))
                .build();
    }

    @Override
    public List<Consultation> findAll() throws Exception {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(resultToConsultation(rs));
            }
        }

        return list;
    }

    @Override
    public Optional<Consultation> findById(Integer idConsult) throws Exception {
        String sql = "SELECT * FROM Consultation WHERE idConsult = ?";
        Consultation consultation = null;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idConsult);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    consultation = resultToConsultation(rs);
                }
            }
        }

        return Optional.ofNullable(consultation);
    }

    @Override
    public List<Consultation> findByDossier(Integer idDossier) throws Exception {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation WHERE idDossier = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idDossier);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(resultToConsultation(rs));
                }
            }
        }

        return list;
    }

    @Override
    public Consultation save(Consultation consultation) throws Exception {
        String sql = "INSERT INTO Consultation " +
                "(dateConsult, observationMedecin, idDossier, idStatut) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, new java.sql.Date(consultation.getDateConsult().getTime()));
            ps.setString(2, consultation.getObservationMedecin());
            ps.setInt(3, consultation.getIdDossier());
            ps.setInt(4, consultation.getIdStatut());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    consultation.setIdConsult(rs.getInt(1));
                }
            }
        }

        return consultation;
    }

    @Override
    public void update(Consultation consultation) throws Exception {
        String sql = "UPDATE Consultation SET " +
                "dateConsult = ?, observationMedecin = ?, idDossier = ?, idStatut = ? " +
                "WHERE idConsult = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(consultation.getDateConsult().getTime()));
            ps.setString(2, consultation.getObservationMedecin());
            ps.setInt(3, consultation.getIdDossier());
            ps.setInt(4, consultation.getIdStatut());
            ps.setInt(5, consultation.getIdConsult());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer idConsult) throws Exception {
        String sql = "DELETE FROM Consultation WHERE idConsult = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idConsult);
            ps.executeUpdate();
        }
    }
}
