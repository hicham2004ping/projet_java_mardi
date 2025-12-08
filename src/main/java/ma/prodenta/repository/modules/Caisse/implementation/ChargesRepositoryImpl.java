// repository/modules/Caisse/implementation/ChargesRepositoryImpl.java
package ma.prodenta.repository.modules.caisse.implementation;


import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.modules.caisse.api.ChargesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChargesRepositoryImpl implements ChargesRepository {

    private final Connection connection;

    public ChargesRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Charges findById(Integer id) throws Exception {
        String sql = "SELECT * FROM charges WHERE id_charge = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Charges> findAll() throws Exception {
        String sql = "SELECT * FROM charges";
        List<Charges> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public Charges save(Charges charge) throws Exception {
        String sql = "INSERT INTO charges " +
                "(titre, description, montant, date_charge, id_cabinet) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, charge.getTitre());
            ps.setString(2, charge.getDescription());
            ps.setDouble(3, charge.getMontant());
            ps.setDate(4, new java.sql.Date(charge.getDateCharge().getTime()));
            ps.setInt(5, charge.getIdCabinet());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    charge.setIdCharge(keys.getLong(1));
                }
            }
        }
        return charge;
    }

    @Override
    public Charges update(Charges charge) throws Exception {
        String sql = "UPDATE charges SET " +
                "titre = ?, description = ?, montant = ?, date_charge = ?, id_cabinet = ? " +
                "WHERE id_charge = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, charge.getTitre());
            ps.setString(2, charge.getDescription());
            ps.setDouble(3, charge.getMontant());
            ps.setDate(4, new java.sql.Date(charge.getDateCharge().getTime()));
            ps.setInt(5, charge.getIdCabinet());
            ps.setLong(6, charge.getIdCharge());
            ps.executeUpdate();
        }
        return charge;
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM charges WHERE id_charge = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Charges mapRow(ResultSet rs) throws SQLException {
        Charges c = new Charges();
        c.setIdCharge(rs.getLong("id_charge"));
        c.setTitre(rs.getString("titre"));
        c.setDescription(rs.getString("description"));
        c.setMontant(rs.getDouble("montant"));
        c.setDateCharge(rs.getDate("date_charge"));
        c.setIdCabinet(rs.getInt("id_cabinet"));
        return c;
    }
}
