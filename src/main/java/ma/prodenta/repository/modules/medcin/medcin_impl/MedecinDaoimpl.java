package ma.prodenta.repository.modules.medcin.medcin_impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Medecin;
import ma.prodenta.repository.modules.medcin.api.MedecinDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinDaoimpl implements MedecinDao {

    @Override
    public List<Medecin> findAll() throws Exception {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM medecin";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Medecin med = mapToMedecin(rs);
                medecins.add(med);
            }
        }

        return medecins;
    }

    @Override
    public Medecin findById(Integer id) throws Exception {
        String sql = "SELECT * FROM medecin WHERE idUser = ?";
        Medecin med = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                med = mapToMedecin(rs);
            }
        }

        return med;
    }

    @Override
    public boolean create(Medecin med) throws SQLException, IOException {
        String sql = "INSERT INTO medecin (idUser, specialite, agendaMensuel) VALUES (?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, med.getIdUser());
            pst.setString(2, med.getSpecialite());
            pst.setString(3, med.getAgendaMensuel());

            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public void update(Medecin med) throws SQLException, IOException, Exception {
        String sql = "UPDATE medecin SET specialite = ?, agendaMensuel = ? WHERE idUser = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, med.getSpecialite());
            pst.setString(2, med.getAgendaMensuel());
            pst.setInt(3, med.getIdUser());

            pst.executeUpdate();
        }
    }

    @Override
    public boolean delete(Medecin med) throws SQLException, Exception {
        return deleteById(med.getIdUser());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException, Exception {
        String sql = "DELETE FROM medecin WHERE idUser = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty(); // Non applicable pour Medecin
    }

    /**
     * Retourne le dernier idUser dans la table medecin
     */
    public Integer get_last_id() {
        String sql = "SELECT MAX(idUser) AS last_id FROM medecin";
        Integer lastId = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("last_id");
                if (rs.wasNull()) {
                    lastId = null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lastId;
    }

    public Medecin mapToMedecin(ResultSet rs) throws SQLException {
        if (rs == null) return null;

        return Medecin.builder()
                .idUser(rs.getInt("idUser"))
                .specialite(rs.getString("specialite"))
                .agendaMensuel(rs.getString("agendaMensuel"))
                .build();
    }
}
