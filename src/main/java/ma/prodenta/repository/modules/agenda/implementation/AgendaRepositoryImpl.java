package ma.prodenta.repository.modules.agenda.implementation;

import ma.prodenta.entities.En.Agenda;
import ma.prodenta.repository.modules.agenda.api.AgendaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendaRepositoryImpl implements AgendaRepository {

    private final Connection connection;

    public AgendaRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Agenda findById(Integer id) throws Exception {
        String sql = "SELECT * FROM agenda WHERE id_agenda = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public List<Agenda> findAll() throws Exception {
        String sql = "SELECT * FROM agenda";
        List<Agenda> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public List<Agenda> findByDate(Date date) throws Exception {
        String sql = "SELECT * FROM agenda WHERE DATE(date_debut) = ?";
        List<Agenda> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(date.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public Agenda save(Agenda agenda) throws Exception {
        String sql = "INSERT INTO agenda " +
                "(id_medecin, id_patient, date_debut, date_fin, statut, note) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, agenda.getIdMedecin());
            ps.setInt(2, agenda.getIdPatient());
            ps.setTimestamp(3, new java.sql.Timestamp(agenda.getDateDebut().getTime()));
            ps.setTimestamp(4, new java.sql.Timestamp(agenda.getDateFin().getTime()));
            ps.setString(5, agenda.getStatut());
            ps.setString(6, agenda.getNote());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) agenda.setIdAgenda(keys.getInt(1));
            }
        }
        return agenda;
    }

    @Override
    public Agenda update(Agenda agenda) throws Exception {
        String sql = "UPDATE agenda SET " +
                "id_medecin = ?, id_patient = ?, date_debut = ?, date_fin = ?, " +
                "statut = ?, note = ? WHERE id_agenda = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, agenda.getIdMedecin());
            ps.setInt(2, agenda.getIdPatient());
            ps.setTimestamp(3, new java.sql.Timestamp(agenda.getDateDebut().getTime()));
            ps.setTimestamp(4, new java.sql.Timestamp(agenda.getDateFin().getTime()));
            ps.setString(5, agenda.getStatut());
            ps.setString(6, agenda.getNote());
            ps.setInt(7, agenda.getIdAgenda());
            ps.executeUpdate();
        }
        return agenda;
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM agenda WHERE id_agenda = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Agenda mapRow(ResultSet rs) throws SQLException {
        Agenda a = new Agenda();
        a.setIdAgenda(rs.getInt("id_agenda"));
        a.setIdMedecin(rs.getInt("id_medecin"));
        a.setIdPatient(rs.getInt("id_patient"));
        a.setDateDebut(rs.getTimestamp("date_debut"));
        a.setDateFin(rs.getTimestamp("date_fin"));
        a.setStatut(rs.getString("statut"));
        a.setNote(rs.getString("note"));
        return a;
    }
}

