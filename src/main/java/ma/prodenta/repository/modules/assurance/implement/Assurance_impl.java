package ma.prodenta.repository.modules.assurance.implement;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.repository.common.Connextion_db;
import ma.prodenta.entities.Enum.Assurance;
import java.io.IOException;
import java.sql.*;
import ma.prodenta.repository.modules.assurance.api.Assurance_api;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.entities.En.Assurance_c;
public class Assurance_impl implements  Assurance_api {
    @Override
    public int map_to_int(Assurance_c a) {
        return switch(a.getLibelle()){
            case CNOPS -> 1;
            case CNSS -> 2;
            case RAMED -> 3;
            case Aucune -> 4;
            default -> 1;
        };
    }

    @Override
    public Assurance map_to_enum(int id ) {
      return switch(id){
            case 1-> Assurance.CNOPS;
            case 2 ->Assurance.CNSS;
            case 3 ->Assurance.RAMED;
            case 4 ->Assurance.Aucune;
          default ->  Assurance.CNOPS;
        };
    }

    @Override
    public Assurance_c findById(Integer id) throws Exception {

        Assurance_c assuranceC = null;

        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            PreparedStatement stmt =
                    conn.prepareStatement("SELECT * FROM assurance WHERE idassurance=?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                assuranceC = new Assurance_c();
                assuranceC.setId(rs.getInt("idassurance"));
                assuranceC.setLibelle(map_to_enum(rs.getInt("idassurance")));
            }
        }

        return assuranceC;
    }


    @Override
    public boolean create(Assurance_c objet) throws SQLException, IOException {
        return false;
    }

    @Override
    public void update(Assurance_c objet) {

        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            PreparedStatement stmt =
                    conn.prepareStatement("UPDATE assurance SET libelle=? WHERE idassurance=?");

            stmt.setString(1, objet.getLibelle().name());
            stmt.setInt(2, objet.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean delete(Assurance_c objet) throws SQLException {
        return deleteById(objet.getId());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {

        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            PreparedStatement stmt =
                    conn.prepareStatement("DELETE FROM assurance WHERE idassurance=?");

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Assurance_c> findAll() throws Exception,IOException,SQLException {
        List<Assurance_c> assuranceCS =new ArrayList<>();
        Assurance_c assuranceC =new Assurance_c();
        try(Connection conn= DriverManager.getConnection(new Connextion_db().getUrl(),new Connextion_db().getUsername(),new Connextion_db().getPassword())){
            PreparedStatement ps=conn.prepareStatement("select * from assurance");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                assuranceC.setLibelle(map_to_enum(rs.getInt("idassurance")));
                assuranceC.setId(rs.getInt("idassurance"));
                assuranceCS.add(assuranceC);
                assuranceC =new Assurance_c();
            }
            return assuranceCS;
        }
    }

    @Override
    public Assurance_c find_by_name(String nom) throws SQLException,IOException{
        Assurance_c assuranceC =new Assurance_c();
        try(Connection conn=DriverManager.getConnection(new Connextion_db().getUrl(),new Connextion_db().getUsername(),new Connextion_db().getPassword())){
            PreparedStatement stmt=conn.prepareStatement("select * from assurance where libelle like ?");
            stmt.setString(1,"%"+nom+"%");
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                 assuranceC.setId(rs.getInt("idassurance"));
                 assuranceC.setLibelle(map_to_enum(rs.getInt("idassurance")));
            }
           return assuranceC;
        }
    }

    @Override
    public int get_last_id() throws SQLException {

        int id = 0;

        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            PreparedStatement stmt =
                    conn.prepareStatement("SELECT MAX(idassurance) FROM assurance");
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                id = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    public static void main(String[] args){
        Assurance_c a=new Assurance_c();
        Assurance_c a1=new Assurance_c();
        Assurance_c a2=new Assurance_c();
        Assurance_impl as=new Assurance_impl();
        List<Assurance_c> assuranceCS;
        try{
            assuranceCS =as.findAll();
            a1=as.find_by_name("cnops");
            a2=as.findById(1);
            System.out.println("l'objet c'est ");
            System.out.println(a1.getLibelle()+" et son id est "+a1.getId());
            for(Assurance_c assuranceC : assuranceCS){
                System.out.println(assuranceC.getLibelle());
            }
         //   int id=as.find_by_name("cnops");
           // System.out.println(id);
        }

        catch(Exception e){
            System.out.println(e);
        }
    }
}
