package ma.prodenta.repository.modules.intervention_medcin.impl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Intervention;
import ma.prodenta.repository.modules.intervention_medcin.api.Intervention_api;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
public class Intervention_impl  implements Intervention_api {

    public Intervention map_resultset_to_objet(ResultSet resultSet) throws SQLException,Exception {
        Intervention intervention = new Intervention();
        intervention.setId(resultSet.getInt("id"));
        intervention.setNumero_dent(resultSet.getInt("numero_dent"));
        intervention.setPrix_patient(resultSet.getInt("prix_patient"));
        int id =resultSet.getInt("id_acte");
        try{
            intervention.setActe(new Acte_impl().findById(id));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return intervention;
    }


    @Override
    public int numero_intervention_par_dent() {
        String sql = "SELECT COUNT(DISTINCT numero_dent) FROM intervention_medcin";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int total_intervention() {
        String sql = "SELECT COUNT(*) FROM intervention_medcin";
        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Intervention> findAll() throws Exception {
        List<Intervention> interventions = new ArrayList<>();

        String sql = "SELECT * FROM intervention_medcin";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {
                Intervention intervention = new Intervention();

                intervention.setId(rs.getInt("id_intervention"));
                intervention.setNumero_dent(rs.getInt("numero_dent"));
                intervention.setPrix_patient(rs.getInt("prix_patient"));
                // acte laissé null -> à mapper si ton modèle le permet

                interventions.add(intervention);
            }
        }
        return interventions;
    }

    @Override
    public Intervention findById(Integer integer) throws Exception {
        String sql = "SELECT * FROM intervention_medcin WHERE id_intervention = ?";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setInt(1, integer);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    Intervention intervention = new Intervention();

                    intervention.setId(rs.getInt("id_intervention"));
                    intervention.setNumero_dent(rs.getInt("numero_dent"));
                    intervention.setPrix_patient(rs.getInt("prix_patient"));
                    // acte à compléter si nécessaire

                    return intervention;
                }
            }
        }
        return null;

    }

    @Override
    public boolean create(Intervention objet) throws SQLException, IOException {
       String  requete= """
               insert into intervention_medcin values(?,?,?,?)
               """;
       int n=0;
       try(Connection conn= SessionFactory.getInstance().getConnection();
           PreparedStatement pst= conn.prepareStatement(requete);)
       {
           pst.setInt(1,objet.getId());
           pst.setInt(2,objet.getNumero_dent());
           pst.setInt(3,objet.getPrix_patient());
           pst.setInt(4,objet.getActe().getId());
           n=pst.executeUpdate();
       }
       return n>0;
    }

    @Override
    public void update(Intervention objet) throws SQLException, IOException, Exception {

        String sql = """
                UPDATE intervention_medcin
                SET numero_dent = ?,
                    prix_patient = ?,
                    id_acte = ?
                WHERE id_intervention = ?
                """;

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setInt(1, objet.getNumero_dent());
            pst.setInt(2, objet.getPrix_patient());
            pst.setInt(3, objet.getActe().getId());
            pst.setInt(4, objet.getId());

            pst.executeUpdate();
        }
    }

    @Override
    public boolean delete(Intervention objet) throws SQLException, Exception {
        if (objet == null) return false;
        return deleteById(objet.getId());

    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException, Exception {
        String sql = "DELETE FROM intervention_medcin WHERE id_intervention = ?";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setInt(1, integer);
            return pst.executeUpdate() > 0;
        }

    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}
