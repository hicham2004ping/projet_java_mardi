package ma.prodenta.repository.modules.consultation.impl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.repository.modules.consultation.api.ConsultationDao;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class ConsultationDaoimpl implements ConsultationDao {
    @Override
    public List<Consultation> findByDossier(Integer idDossier) throws Exception {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation WHERE idDossier = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idDossier);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(resultToConsultation(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<Consultation> findAll() throws Exception {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(resultToConsultation(rs));
            }
        }
        return list;
    }

    @Override
    public Consultation findById(Integer integer) throws Exception {
        String requete= """
                select * from consultation where idConsult=?
                """;
        Consultation consultation=new Consultation();
        try (Connection c = SessionFactory.getInstance().getConnection();
        PreparedStatement ps = c.prepareStatement(requete)){
            ps.setInt(1, integer);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consultation= resultToConsultation(rs);
            }
            return consultation;
        }
    }

    @Override
    public boolean create(Consultation objet) throws SQLException, IOException {
        String sql = """
                INSERT INTO Consultation
               (dateConsult, observationMedecin, idDossier,idStatut,id_rdv,id_medecin)
               VALUES (?, ?, ?, ?,?,?)
               """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, new java.sql.Date(objet.getDateConsult().getTime()));
            ps.setString(2, objet.getObservationMedecin());
            ps.setInt(3, objet.getIdDossier());
            ps.setInt(4, objet.getIdStatut());
            ps.setInt(5,objet.getId_rdv());
            ps.setInt(6, objet.getId_medecin());
            boolean flag= ps.executeUpdate() > 0;
            if (flag) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    objet.setIdConsult(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public void update(Consultation objet) throws SQLException, IOException, Exception {
        String sql = """
                update consultation set dateConsult=?,observationMedecin=?,idDossier=?,idStatut=?,id_rdv=?,id_medecin=?
                where idConsult=?
               """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            System.out.println("je suis dans la methode update et l'id est "+objet.getIdConsult());
            ps.setDate(1, new java.sql.Date(objet.getDateConsult().getTime()));
            ps.setString(2, objet.getObservationMedecin());
            ps.setInt(3, objet.getIdDossier());
            ps.setInt(4, objet.getIdStatut());
            ps.setInt(5,objet.getId_rdv());
            ps.setInt(6, objet.getId_medecin());
            ps.setInt(7,objet.getIdConsult());
            ps.executeUpdate() ;
        }
    }

    @Override
    public boolean delete(Consultation objet) throws SQLException, Exception {
        String sql = "DELETE FROM Consultation WHERE idConsult = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, objet.getIdConsult());
           return  ps.executeUpdate()>0;
        }
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException, Exception {
        return delete(findById(integer));
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
    private Consultation resultToConsultation(ResultSet rs) throws SQLException {
        return new Consultation(
                rs.getInt("idConsult"),
                rs.getDate("dateConsult"),
                rs.getString("observationMedecin"),
                rs.getInt("idDossier"),
                rs.getInt("idStatut"),
                rs.getInt("id_medecin"),
                rs.getInt("id_rdv")
        );
    }
    public int last_id(){
        String requete= """
                select max(idConsult) from Consultation;
                """;
        int id=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement stmt=conn.prepareStatement(requete);)
        {
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                id=rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int get_last_id() {
        int lastId = 0;
        String sql = "SELECT MAX(idConsult) AS last_id FROM Consultation";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du dernier ID Consultation : " + e.getMessage());
        }

        return lastId;
    }

@Override
    // Récupère toutes les consultations liées à un RDV
    public List<Consultation> findByRdv(Integer idRdv) throws Exception {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation WHERE id_rdv = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idRdv);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Consultation(
                            rs.getInt("idConsult"),
                            rs.getDate("dateConsult"),
                            rs.getString("observationMedecin"),
                            rs.getInt("idDossier"),
                            rs.getInt("idStatut"),
                            rs.getInt("id_medecin"),
                            rs.getInt("id_rdv")
                    ));
                }
            }
        }
        return list;
    }
}