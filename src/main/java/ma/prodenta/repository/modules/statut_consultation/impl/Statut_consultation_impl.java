package ma.prodenta.repository.modules.statut_consultation.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Staut_consultation;
import ma.prodenta.repository.modules.statut_consultation.api.Statut_consultation_api;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Statut_consultation_impl implements Statut_consultation_api {

    private static final String TABLE = "statut_consultation";
    @Override
    public Staut_consultation findBYnom(String libelle) {

        String sql = "SELECT * FROM " + TABLE + " WHERE libelle = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libelle);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Staut_consultation> findAll() throws Exception {

        String sql = "SELECT * FROM " + TABLE;

        List<Staut_consultation> list = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }

        return list;
    }

    @Override
    public Staut_consultation findById(Integer id) throws Exception {

        String sql = "SELECT * FROM " + TABLE + " WHERE idStatut = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }
        }

        return null;
    }

    @Override
    public boolean create(Staut_consultation objet) throws SQLException, IOException {

        String sql = "INSERT INTO " + TABLE + " (libelle) VALUES (?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, objet.getLibelle());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public void update(Staut_consultation objet) throws SQLException, IOException, Exception {

        String sql = "UPDATE " + TABLE + " SET libelle = ? WHERE idStatut = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, objet.getLibelle());
            ps.setInt(2, objet.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(Staut_consultation objet) throws SQLException, Exception {
        return deleteById(objet.getId());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException, Exception {

        String sql = "DELETE FROM " + TABLE + " WHERE idStatut = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    // ============================= NON UTILISÉ =============================
    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    // ============================= MAPPING =============================
    private Staut_consultation mapRow(ResultSet rs) throws SQLException {

        Staut_consultation s = new Staut_consultation();

        s.setId(rs.getInt("idStatut"));
        s.setLibelle(rs.getString("libelle"));

        return s;
    }
}
