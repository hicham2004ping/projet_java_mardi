package ma.prodenta.repository.modules.medicament.fileBase_implementation;

import ma.prodenta.repository.modules.medicament.api.MedicamentDao;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.config.SessionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAOImpl implements MedicamentDao {

    @Override
    public void create(Medicament medicament) throws Exception {
        String sql = "INSERT INTO medicament(nom, laboratoire, type, remboursable, prixUnit, description, idForme) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicament.getNom());
            ps.setString(2, medicament.getLaboratoire());
            ps.setString(3, medicament.getType());
            ps.setBoolean(4, medicament.getRemboursable());
            ps.setDouble(5, medicament.getPrixUnit());
            ps.setString(6, medicament.getDescription());
            ps.setInt(7, medicament.getIdForme());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Medicament medicament) throws Exception {
        String sql = "UPDATE medicament SET nom=?, laboratoire=?, type=?, remboursable=?, prixUnit=?, description=?, idForme=? WHERE idMed=?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicament.getNom());
            ps.setString(2, medicament.getLaboratoire());
            ps.setString(3, medicament.getType());
            ps.setBoolean(4, medicament.getRemboursable());
            ps.setDouble(5, medicament.getPrixUnit());
            ps.setString(6, medicament.getDescription());
            ps.setInt(7, medicament.getIdForme());
            ps.setInt(8, medicament.getIdMed());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int idMed) throws Exception {
        String sql = "DELETE FROM medicament WHERE idMed=?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMed);
            ps.executeUpdate();
        }
    }

    @Override
    public Medicament findById(int idMed) throws Exception {
        String sql = "SELECT * FROM medicament WHERE idMed=?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMed);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Medicament(
                            rs.getInt("idMed"),
                            rs.getString("nom"),
                            rs.getString("laboratoire"),
                            rs.getString("type"),
                            rs.getBoolean("remboursable"),
                            rs.getDouble("prixUnit"),
                            rs.getString("description"),
                            rs.getInt("idForme")
                    );
                }
            }
        }
        return null;
    }


    @Override
    public List<Medicament> findAll() throws Exception {
        List<Medicament> liste = new ArrayList<>();
        String sql = "SELECT * FROM medicament";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Medicament(
                        rs.getInt("idMed"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getString("type"),
                        rs.getBoolean("remboursable"),
                        rs.getDouble("prixUnit"),
                        rs.getString("description"),
                        rs.getInt("idForme")
                ));
            }
        }
        return liste;
    }
}
