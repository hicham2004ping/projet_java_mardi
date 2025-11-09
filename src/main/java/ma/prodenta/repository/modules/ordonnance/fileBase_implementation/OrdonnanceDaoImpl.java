package ma.prodenta.repository.modules.ordonnance.fileBase_implementation;

import ma.prodenta.repository.modules.ordonnance.api.OrdonnanceDao;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceDaoImpl implements OrdonnanceDao {

    @Override
    public void create(Ordonnance ordonnance) throws Exception {
        String sql = "INSERT INTO ordonnance(dateOrd, idDossier) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(ordonnance.getDateOrd().getTime()));
            ps.setInt(2, ordonnance.getIdDossier());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Ordonnance ordonnance) throws Exception {
        String sql = "UPDATE ordonnance SET dateOrd=?, idDossier=? WHERE idOrd=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(ordonnance.getDateOrd().getTime()));
            ps.setInt(2, ordonnance.getIdDossier());
            ps.setInt(3, ordonnance.getIdOrd());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int idOrd) throws Exception {
        String sql = "DELETE FROM ordonnance WHERE idOrd=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOrd);
            ps.executeUpdate();
        }
    }

    @Override
    public Ordonnance findById(int idOrd) throws Exception {
        String sql = "SELECT * FROM ordonnance WHERE idOrd=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOrd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ordonnance(
                            rs.getInt("idOrd"),
                            rs.getDate("dateOrd"),
                            rs.getInt("idDossier")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Ordonnance> findAll() throws Exception {
        List<Ordonnance> liste = new ArrayList<>();
        String sql = "SELECT * FROM ordonnance";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Ordonnance(
                        rs.getInt("idOrd"),
                        rs.getDate("dateOrd"),
                        rs.getInt("idDossier")
                ));
            }
        }
        return liste;
    }
}
