package ma.prodenta.repository.modules.SituationFinanciere.impl;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.SituationFinanciere;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.SituationFinanciere.api.SituationFinancierDao;
import ma.prodenta.config.SessionFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SituationFinanciereDaoImpl implements SituationFinancierDao {

    @Override
    public List<SituationFinanciere> findAll() throws Exception {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(resultToSF(rs));
        }

        return list;
    }

    @Override
    public SituationFinanciere findById(Long id) throws Exception {
        String sql = "SELECT * FROM SituationFinanciere WHERE idSF = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return resultToSF(rs);
            }
        }
        return null;
    }

    @Override
    public boolean create(SituationFinanciere sf) throws SQLException, IOException {
        String sql = """
            INSERT INTO SituationFinanciere(totalActes, totalPaye, credit, statut, enPromo, idPatient)
            VALUES(?,?,?,?,?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, sf.getTotalActes());
            ps.setDouble(2, sf.getTotalPaye());
            ps.setDouble(3, sf.getCredit());
            ps.setString(4, sf.getStatut());
            ps.setString(5, sf.getEnPromo());
            ps.setInt(6, sf.getIdPatient());

            int s = ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) sf.setIdSF(keys.getInt(1));
            }

            return s > 0;
        }
    }

    @Override
    public Utilisateur update(SituationFinanciere sf) throws Exception {
        String sql = """
            UPDATE SituationFinanciere
            SET totalActes=?, totalPaye=?, credit=?, statut=?, enPromo=?, idPatient=?
            WHERE idSF=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, sf.getTotalActes());
            ps.setDouble(2, sf.getTotalPaye());
            ps.setDouble(3, sf.getCredit());
            ps.setString(4, sf.getStatut());
            ps.setString(5, sf.getEnPromo());
            ps.setInt(6, sf.getIdPatient());
            ps.setInt(7, sf.getIdSF());

            ps.executeUpdate();
        }
        return null;
    }

    @Override
    public boolean delete(SituationFinanciere sf) throws Exception {
        return deleteById(sf.getIdSF().longValue());
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        String sql = "DELETE FROM SituationFinanciere WHERE idSF = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    // Convertisseur ResultSet → SituationFinanciere
    private SituationFinanciere resultToSF(ResultSet rs) throws SQLException {
        return SituationFinanciere.builder()
                .idSF(rs.getInt("idSF"))
                .totalActes(rs.getDouble("totalActes"))
                .totalPaye(rs.getDouble("totalPaye"))
                .credit(rs.getDouble("credit"))
                .statut(rs.getString("statut"))
                .enPromo(rs.getString("enPromo"))
                .idPatient(rs.getInt("idPatient"))
                .build();
    }
}
