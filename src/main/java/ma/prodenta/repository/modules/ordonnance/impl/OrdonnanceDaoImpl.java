package ma.prodenta.repository.modules.ordonnance.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.*;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.ordonnance.api.Ordonance_api;
import java.net.Inet4Address;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceDaoImpl implements Ordonance_api {
    @Override
    public Ordonnance findById(Integer idOrd) throws Exception {
        String sql = "SELECT * FROM ordonnance WHERE idOrd = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idOrd);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ordonnance(
                            rs.getLong("idOrd"),
                            rs.getDate("dateOrd").toLocalDate(),
                            rs.getInt("idDossier"),
                            rs.getInt("id_conultation")
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
                        rs.getDate("dateOrd").toLocalDate(),
                        rs.getInt("idDossier"),
                        rs.getInt("id_conultation")
                ));
            }
        }
        return liste;
    }

    @Override
    public boolean create(Ordonnance ord) throws SQLException {
        int n = 0;
        String sql = "INSERT INTO Ordonnance (dateOrd, idDossier,id_conultation) VALUES (?,?,?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1,Date.valueOf(ord.getDateOrd()));
            stmt.setInt(2, ord.getIdDossier());
            stmt.setInt(3, ord.getIdconsultation());
            boolean flag= stmt.executeUpdate()>0;
            ResultSet rs = stmt.getGeneratedKeys();
            if(flag){
                if(rs.next()){
                    ord.setIdOrd(rs.getLong(1));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public void update(Ordonnance ord) throws Exception {
        String sql = "UPDATE Ordonnance SET dateOrd = ?, idDossier = ? WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1,Date.valueOf(ord.getDateOrd()));
            stmt.setInt(2, ord.getIdDossier());
            stmt.setLong(3, ord.getIdOrd());

            stmt.executeUpdate();
        }
    }

    @Override
    public boolean delete(Ordonnance ord) throws Exception {
        int n = 0;
        String sql = "DELETE FROM Ordonnance WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ord.getIdOrd());
            n = stmt.executeUpdate();
        }

        return n > 0;
    }

    @Override
    public boolean deleteById(Integer idOrd) throws Exception {
        int n = 0;
        String sql = "DELETE FROM Ordonnance WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idOrd);
            n = stmt.executeUpdate();
        }

        return n > 0;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    @Override
    public List<Medicament> find_all_medicament_in_ordonance(Ordonnance ordonance) {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = """
            select m.idMed,m.nom,m.laboratoire,m.type,m.remboursable,m.prixUnit,m.description,m.idForme
            from prescription p,medicament m
            where
            p.idMed=m.idMed
            and
            p.idOrd=?
        """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ordonance.getIdOrd());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medicaments.add(new Medicament(
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicaments;
    }

    @Override
    public int Total_ordonance(Ordonnance ordonance) {
        String sql = "SELECT SUM(m.prixUnit) " +
                "FROM medicament m " +
                "JOIN ordonnance_medicament om ON om.idMed = m.idMed " +
                "WHERE om.idOrd = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ordonance.getIdOrd());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Ordonnance> list_ordonances_dossier(int idDossier) throws Exception {
        List<Ordonnance> ordonances = new ArrayList<>();
        Ordonnance ordonance = new Ordonnance();
        String requete= """
                select * from ordonnance where idDossier=?
                """;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete))
        {
            ps.setInt(1, idDossier);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ordonance.setIdDossier(idDossier);
                ordonance.setDateOrd(rs.getDate("dateOrd").toLocalDate());
                ordonance.setIdOrd(rs.getLong("idOrd"));
                ordonance.setIdconsultation(rs.getInt("id_Conultation"));
                ordonances.add(ordonance);
                ordonance = new Ordonnance();
            }
            return ordonances;
        }
    }

    @Override
    public List<Ordonnance> consulterOrdonnancesParConsultation(Integer idConsultation) throws SQLException {
        String  requete = """
        select * from  ordonnance where id_conultation=?
        """;
        List<Ordonnance> ordonances = new ArrayList<>();
        Ordonnance ordonance = new Ordonnance();
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement stmt=conn.prepareStatement(requete))
        {
            stmt.setInt(1, idConsultation);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ordonance.setIdconsultation(idConsultation);
                ordonance.setDateOrd(rs.getDate("dateOrd").toLocalDate());
                ordonance.setIdDossier(rs.getInt("idDossier"));
                ordonance.setIdOrd(rs.getLong("idOrd"));
                ordonances.add(ordonance);
                ordonance = new Ordonnance();
            }
            return ordonances;
        }
    }

    @Override
    public double calculerCoutTotal(Ordonnance ordonnance) throws SQLException {
        String requete= """
                select sum(p.quantite*m.prixUnit)
                from
                prescription p, medicament m
                where
                p.idMed=m.idMed
                and
                p.idOrd=?
                """;
        double total = 0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement stmt=conn.prepareStatement(requete))
        {
            stmt.setInt(1,Math.toIntExact(ordonnance.getIdOrd()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total=rs.getDouble(1);
            }
            return total;
        }
    }

    @Override
    public List<Prescription> list_Prescriptions(Ordonnance ordonnance) throws Exception {
        String requete= """
                select * from prescription where idOrd=?
                """;
        List<Prescription> prescriptions = new ArrayList<>();
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement stmt=conn.prepareStatement(requete)){
            stmt.setInt(1, Math.toIntExact(ordonnance.getIdOrd()));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                prescriptions.add(new Prescription(
                        rs.getInt("idPr"),
                        rs.getInt("quantite"),
                        rs.getString("frequence"),
                        rs.getInt("dureeEnJours"),
                        Math.toIntExact(ordonnance.getIdOrd()),
                        rs.getInt("idMed")
                ));
            }
            return prescriptions;
        }
    }

    public int last_id(){
        String requete= """
                select max(idOrd) from ordonnance;
                """;
        int id=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(requete);){
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                id=rs.getInt(1);
            }
            return  id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
