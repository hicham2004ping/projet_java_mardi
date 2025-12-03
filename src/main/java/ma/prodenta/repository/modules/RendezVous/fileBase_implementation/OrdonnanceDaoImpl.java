package ma.prodenta.repository.modules.RendezVous.fileBase_implementation;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.repository.modules.RendezVous.api.OrdonnanceDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceDaoImpl implements OrdonnanceDao {

    @Override
    public Ordonnance findById(Long idOrd) throws Exception {
        String sql = "SELECT * FROM ordonnance WHERE idOrd=?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idOrd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ordonnance(
                            rs.getLong("idOrd"),
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
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Ordonnance(
                        rs.getLong("idOrd"),
                        rs.getDate("dateOrd"),
                        rs.getInt("idDossier")
                ));
            }
        }
        return liste;
    }
    @Override
    public void create(Ordonnance ord) throws Exception {

        String sql = "INSERT INTO Ordonnance (dateOrd, idDossier) VALUES (?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new Date(ord.getDateOrd().getTime()));
            stmt.setInt(2, ord.getIdDossier());

            stmt.executeUpdate();
        }
    }


    @Override
    public void update(Ordonnance ord) throws Exception {

        String sql = "UPDATE Ordonnance SET dateOrd = ?, idDossier = ? WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new Date(ord.getDateOrd().getTime()));
            stmt.setInt(2, ord.getIdDossier());
            stmt.setLong(3, ord.getIdOrd());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Ordonnance ord) throws Exception {

        String sql = "DELETE FROM Ordonnance WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1,ord.getIdOrd());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}
