package ma.prodenta.repository.modules.agenda.baseImplementation;

import ma.prodenta.config.util.DBConnection;
import ma.prodenta.entities.En.Agenda;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.repository.modules.agenda.api.AgendaDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AgendaDaoImpl implements AgendaDao {

    @Override
    public List<Agenda> findAll() throws Exception {
        List<Agenda> agendaList = new ArrayList<>();
        String sql = "SELECT * FROM agenda";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                agendaList.add(mapResultSetToAgenda(rs));
            }
        }
        return agendaList;
    }

    @Override
    public Agenda findById(Integer id) throws Exception {
        String sql = "SELECT * FROM agenda WHERE idAgenda = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAgenda(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean create(Agenda agenda) throws SQLException, IOException {
        String sql = "INSERT INTO agenda (idMedecin, idPatient, dateDebut, dateFin, statut, note) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setStatementParams(ps, agenda);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(Agenda agenda) throws SQLException, IOException, Exception {
        String sql = "UPDATE agenda SET idMedecin = ?, idPatient = ?, dateDebut = ?, dateFin = ?, statut = ?, note = ? WHERE idAgenda = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setStatementParams(ps, agenda);
            ps.setInt(7, agenda.getIdAgenda());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(Agenda agenda) throws SQLException, Exception {
        if (agenda == null || agenda.getIdAgenda() == null)
            return false;
        return deleteById(agenda.getIdAgenda());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException, Exception {
        String sql = "DELETE FROM agenda WHERE idAgenda = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    @Override
    public List<Agenda> findByDate(Date date) throws Exception {
        List<Agenda> agendaList = new ArrayList<>();
        String sql = "SELECT * FROM agenda WHERE DATE(dateDebut) = DATE(?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (date != null) {
                ps.setDate(1, new java.sql.Date(date.getTime()));
            } else {
                return agendaList;
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    agendaList.add(mapResultSetToAgenda(rs));
                }
            }
        }
        return agendaList;
    }

    @Override
    public List<Agenda> findByMedecin(Integer idMedecin) throws Exception {
        List<Agenda> agendaList = new ArrayList<>();
        String sql = "SELECT * FROM agenda WHERE idMedecin = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMedecin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    agendaList.add(mapResultSetToAgenda(rs));
                }
            }
        }
        return agendaList;
    }

    private Agenda mapResultSetToAgenda(ResultSet rs) throws SQLException {
        return Agenda.builder()
                .idAgenda(rs.getInt("idAgenda"))
                .idMedecin(rs.getObject("idMedecin") != null ? rs.getInt("idMedecin") : null)
                .idPatient(rs.getObject("idPatient") != null ? rs.getInt("idPatient") : null)
                .dateDebut(rs.getTimestamp("dateDebut"))
                .dateFin(rs.getTimestamp("dateFin"))
                .statut(rs.getString("statut"))
                .note(rs.getString("note"))
                .build();
    }

    private void setStatementParams(PreparedStatement ps, Agenda agenda) throws SQLException {
        if (agenda.getIdMedecin() != null) {
            ps.setInt(1, agenda.getIdMedecin());
        } else {
            ps.setNull(1, java.sql.Types.INTEGER);
        }

        if (agenda.getIdPatient() != null) {
            ps.setInt(2, agenda.getIdPatient());
        } else {
            ps.setNull(2, java.sql.Types.INTEGER);
        }

        if (agenda.getDateDebut() != null) {
            ps.setTimestamp(3, new java.sql.Timestamp(agenda.getDateDebut().getTime()));
        } else {
            ps.setNull(3, java.sql.Types.TIMESTAMP);
        }

        if (agenda.getDateFin() != null) {
            ps.setTimestamp(4, new java.sql.Timestamp(agenda.getDateFin().getTime()));
        } else {
            ps.setNull(4, java.sql.Types.TIMESTAMP);
        }

        ps.setString(5, agenda.getStatut());
        ps.setString(6, agenda.getNote());
    }
}