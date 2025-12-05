package ma.prodenta.repository.modules.actes.impl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.repository.modules.actes.api.Acte_api;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class Acte_impl implements Acte_api {

    @Override
    public int get_last_id() throws SQLException {
        String requete= """
                select max(id) from acte
                """;
        int n=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement pst=conn.prepareStatement(requete);)
        {
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                n=rs.getInt(1);
            }
        }
        return n+1;
    }

    @Override
    public int total_actes() throws SQLException {
       String requete= """
               select count(*) from acte 
               """;
       int n=0;
       try(Connection conn=SessionFactory.getInstance().getConnection();
       PreparedStatement pst=conn.prepareStatement(requete);)
       {
        ResultSet rs=pst.executeQuery();
        if(rs.next()){
            n=rs.getInt(1);
        }
       }
       return n;
    }

    @Override
    public Acte mapResultSetToActe(ResultSet resultSet) throws SQLException {
        if (resultSet==null){
            return null;
        }
        Acte acte=new Acte();
        acte.setId(resultSet.getInt("id"));
        acte.setLibelle(resultSet.getString("libelle"));
        acte.setCategorie(resultSet.getString("categorie"));
        acte.setPrix_de_base(resultSet.getDouble("prix_de_base"));
        return acte ;
    }

    @Override
    public List<Acte> findAll() throws Exception {
        String requete = "select * from acte";
        List<Acte> actes = new ArrayList<>();

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(requete))
        {
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                actes.add(mapResultSetToActe(rs));
            }
        }
        return actes;
    }

    @Override
    public Acte findById(Integer integer) throws Exception {
        String requete = "select * from acte where id = ?";
        Acte acte = null;

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(requete))
        {
            pst.setInt(1, integer);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                acte = mapResultSetToActe(rs);
            }
        }
        return acte;
    }

    @Override
    public boolean create(Acte objet) throws SQLException, IOException {
        String requete= """
                insert into acte values (?,?,?,?)
                """;
        int n=0;
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement pst= conn.prepareStatement(requete);)
        {
            pst.setInt(1,objet.getId());
            pst.setString(2,objet.getCategorie());
            pst.setString(3,objet.getLibelle());
            pst.setDouble(4,objet.getPrix_de_base());
            n= pst.executeUpdate();
            return n>0;
        }
    }

    @Override
    public void update(Acte objet) throws SQLException, IOException, Exception {
        String requete = """
                update acte
                set categorie = ?,
                    libelle = ?,
                    prix_de_base = ?
                where id = ?
                """;

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(requete))
        {
            pst.setString(1, objet.getCategorie());
            pst.setString(2, objet.getLibelle());
            pst.setDouble(3, objet.getPrix_de_base());
            pst.setInt(4, objet.getId());

            pst.executeUpdate();
        }
    }

    @Override
    public boolean delete(Acte objet) throws SQLException, Exception {
        return deleteById(objet.getId());
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException, Exception {
        String requete = "delete from acte where id = ?";
        int n;

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(requete))
        {
            pst.setInt(1, integer);
            n = pst.executeUpdate();
        }

        return n > 0;
    }
    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    public static void main(){
        System.out.println("salut comment ca va");
        Acte_impl acte = new Acte_impl();
        try{
            Acte a1=acte.findById(22);
            int id=acte.get_last_id();
            System.out.println("l'id c'est "+a1.getId()+" sa categorie est "+a1.getCategorie());
        }
        catch(Exception e ){
            System.out.println(e.getMessage());
        }
    }
}
