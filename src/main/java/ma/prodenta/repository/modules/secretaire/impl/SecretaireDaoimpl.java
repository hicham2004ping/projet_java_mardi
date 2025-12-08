package ma.prodenta.repository.modules.secretaire.impl;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Secretaire;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.secretaire.api.SecretaireDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecretaireDaoimpl implements SecretaireDao {

    private Secretaire resultToSecretaire(ResultSet rs) throws SQLException {
        return Secretaire.builder()
                .idUser(rs.getInt("idUser"))
                .numCNSS(rs.getString("numCNSS"))
                .commission(rs.getDouble("commission"))
                .build();
    }

    @Override
    public List<Secretaire> findAll() throws Exception {
        List<Secretaire> list = new ArrayList<>();
        String sql = "SELECT * FROM Secretaire";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(resultToSecretaire(rs));
            }
        }
        return list;
    }

    @Override
    public Secretaire findById(Integer id) throws Exception {
        String sql = "SELECT * FROM Secretaire WHERE idUser = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToSecretaire(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean create(Secretaire secretaire) throws SQLException, IOException {
        String sql = "INSERT INTO Secretaire (idUser, numCNSS, commission) VALUES (?, ?, ?)";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, secretaire.getIdUser());
            ps.setString(2, secretaire.getNumCNSS());
            ps.setDouble(3, secretaire.getCommission());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public void update(Secretaire secretaire) throws SQLException, IOException, Exception {
        String sql = "UPDATE Secretaire SET numCNSS = ?, commission = ? WHERE idUser = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, secretaire.getNumCNSS());
            ps.setDouble(2, secretaire.getCommission());
            ps.setInt(3, secretaire.getIdUser());

            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(Secretaire secretaire) throws SQLException, Exception {
        if (secretaire == null) return false;
        return deleteById(secretaire.getIdUser());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException, Exception {
        String sql = "DELETE FROM Secretaire WHERE idUser = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔴 OBLIGATOIRE SELON TON CRUD, mais inutile pour Secretaire
    @Override
    public Optional<ma.prodenta.entities.En.Antecedent> findByNom(String nom) {
        // Cette méthode n'a aucun sens pour Secretaire, mais ton CRUD l'impose
        return Optional.empty();
    }
}
