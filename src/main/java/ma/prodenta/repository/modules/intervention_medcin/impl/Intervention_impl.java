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
        intervention.setId_consultation(resultSet.getInt("id_consultation"));
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
                intervention.setId_consultation(rs.getInt("id_consultation"));
                intervention.setActe(new Acte_impl().findById(rs.getInt("id_acte")));
                interventions.add(intervention);
            }
        }
        return interventions;
    }

    @Override
    public Intervention findById(Integer integer) throws Exception {
        String sql = """
        SELECT i.id AS i_id, i.numero_dent, i.prix_patient, i.id_consultation,
               a.id AS a_id, a.libelle, a.prix_de_base
        FROM intervention_medcin i
        JOIN acte a ON i.id_acte = a.id
        WHERE i.id = ?
    """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, integer);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Acte acte = new Acte();
                    acte.setId(rs.getInt("a_id"));
                    acte.setLibelle(rs.getString("libelle"));
                    acte.setPrix_de_base(rs.getInt("prix_de_base"));

                    Intervention intervention = new Intervention();
                    intervention.setId(rs.getInt("i_id"));
                    intervention.setNumero_dent(rs.getInt("numero_dent"));
                    intervention.setPrix_patient(rs.getInt("prix_patient"));
                    intervention.setId_consultation(rs.getInt("id_consultation"));
                    intervention.setActe(acte);
                    return intervention;
                }
            }
        }
        return null;
    }

    @Override
    public boolean create(Intervention objet) throws SQLException, IOException {
       String   requete= """
               insert into intervention_medcin (numero_dent,prix_patient,id_acte,id_consultation) values(?,?,?,?)
               """;
       int n=0;
       int prix_patient = objet.getPrix_patient();
       int prix_generale=(int)objet.getActe().getPrix_de_base();

        if(objet.getNumero_dent()>32 || objet.getNumero_dent()<1){
             return false;
         }
       System.out.println("la valeur apres le casting c'est "+prix_generale);

       try(Connection conn= SessionFactory.getInstance().getConnection();
           PreparedStatement pst= conn.prepareStatement(requete);)
       {
           if (prix_patient == -1){
               System.out.println("ca sera le prix par defaut");
               objet.setPrix_patient(prix_generale);
           }
           else{
               System.out.println("ca sera pas le prix par defaut");
               objet.setPrix_patient(prix_patient);
           }
           pst.setInt(1,objet.getNumero_dent());
           pst.setInt(2,objet.getPrix_patient());
           pst.setInt(3,objet.getActe().getId());
           pst.setInt(4,objet.getId_consultation());
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
                WHERE id = ?
                """;
        int prix_patient = objet.getPrix_patient();
        int prix_generale=(int)objet.getActe().getPrix_de_base();
        if(objet.getNumero_dent()>32 || objet.getNumero_dent()<1){
            System.out.println("impossible");
            return ;
        }
        if(prix_patient == -1){
            System.out.println("ca sera le prix par defaut");
            objet.setPrix_patient(prix_generale);
        }
        else{
            System.out.println("ca sera pas le prix par defaut");
            objet.setPrix_patient(prix_patient);
        }
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
        String sql = "DELETE FROM intervention_medcin WHERE id = ?";

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
    public int get_last_id() {
        String requete= """
                select max(id) from intervention_medcin;
                """;
        int id=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement pst=conn.prepareStatement(requete);)
        {
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                id = rs.getInt("max(id)");
            }
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
