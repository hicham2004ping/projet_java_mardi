package ma.prodenta.repository.modules.medcin.medcin_impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Medecin;
import ma.prodenta.repository.modules.medcin.api.MedecinDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinDaoimpl implements MedecinDao {

    private Medecin resultToMedecin(ResultSet rs) throws SQLException {
        return Medecin.builder()
                .idUser(rs.getInt("idUser"))
                .specialite(rs.getString("specialite"))
                .agendaMensuel(rs.getString("agendaMensuel"))
                .build();
    }

    @Override
    public List<Medecin> findAll() throws Exception {
        List<Medecin> list = new ArrayList<>();
        String sql = "SELECT * FROM Medecin";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(resultToMedecin(rs));
            }
        }
        return list;
    }

    @Override
    public Optional<Medecin> findById(int idUser) throws Exception {
        String sql = "SELECT * FROM Medecin WHERE idUser = ?";
        Medecin medecin = null;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUser);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    medecin = resultToMedecin(rs);
                }
            }
        }

        return Optional.ofNullable(medecin);
    }

    @Override
    public Medecin save(Medecin medecin) throws Exception {
        String sql = "INSERT INTO Medecin (idUser, specialite, agendaMensuel) VALUES (?, ?, ?)";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, medecin.getIdUser());
            ps.setString(2, medecin.getSpecialite());
            ps.setString(3, medecin.getAgendaMensuel());

            ps.executeUpdate();
        }

        return medecin;
    }

    @Override
    public void update(Medecin medecin) throws Exception {
        String sql = "UPDATE Medecin SET specialite = ?, agendaMensuel = ? WHERE idUser = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, medecin.getSpecialite());
            ps.setString(2, medecin.getAgendaMensuel());
            ps.setInt(3, medecin.getIdUser());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int idUser) throws Exception {
        String sql = "DELETE FROM Medecin WHERE idUser = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ps.executeUpdate();
        }
    }
}
