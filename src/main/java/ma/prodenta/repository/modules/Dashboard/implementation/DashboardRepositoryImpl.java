// repository/modules/Dashboard/implementation/DashboardRepositoryImpl.java
package ma.prodenta.repository.modules.Dashboard.implementation;

import ma.prodenta.entities.En.Dashboard;
import ma.prodenta.repository.modules.Dashboard.api.DashboardRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardRepositoryImpl implements DashboardRepository {

    private final Connection connection;

    public DashboardRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Dashboard findById(Integer id) throws Exception {
        String sql = "SELECT * FROM dashboard WHERE id_dashboard = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public List<Dashboard> findAll() throws Exception {
        String sql = "SELECT * FROM dashboard";
        List<Dashboard> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public Dashboard findByPeriode(Date dateDebut, Date dateFin) throws Exception {
        String sql = "SELECT * FROM dashboard WHERE date_debut = ? AND date_fin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(dateDebut.getTime()));
            ps.setDate(2, new java.sql.Date(dateFin.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public Dashboard save(Dashboard dash) throws Exception {
        String sql = "INSERT INTO dashboard " +
                "(date_debut, date_fin, nb_patients, nb_actes, total_recettes, total_depenses) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, new java.sql.Date(dash.getDateDebut().getTime()));
            ps.setDate(2, new java.sql.Date(dash.getDateFin().getTime()));
            ps.setInt(3, dash.getNbPatients());
            ps.setInt(4, dash.getNbActes());
            ps.setDouble(5, dash.getTotalRecettes());
            ps.setDouble(6, dash.getTotalDepenses());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) dash.setIdDashboard(keys.getInt(1));
            }
        }
        return dash;
    }

    @Override
    public Dashboard update(Dashboard dash) throws Exception {
        String sql = "UPDATE dashboard SET " +
                "date_debut = ?, date_fin = ?, nb_patients = ?, nb_actes = ?, " +
                "total_recettes = ?, total_depenses = ? " +
                "WHERE id_dashboard = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(dash.getDateDebut().getTime()));
            ps.setDate(2, new java.sql.Date(dash.getDateFin().getTime()));
            ps.setInt(3, dash.getNbPatients());
            ps.setInt(4, dash.getNbActes());
            ps.setDouble(5, dash.getTotalRecettes());
            ps.setDouble(6, dash.getTotalDepenses());
            ps.setInt(7, dash.getIdDashboard());
            ps.executeUpdate();
        }
        return dash;
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM dashboard WHERE id_dashboard = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Dashboard mapRow(ResultSet rs) throws SQLException {
        Dashboard d = new Dashboard();
        d.setIdDashboard(rs.getInt("id_dashboard"));
        d.setDateDebut(rs.getDate("date_debut"));
        d.setDateFin(rs.getDate("date_fin"));
        d.setNbPatients(rs.getInt("nb_patients"));
        d.setNbActes(rs.getInt("nb_actes"));
        d.setTotalRecettes(rs.getDouble("total_recettes"));
        d.setTotalDepenses(rs.getDouble("total_depenses"));
        return d;
    }
}
