package ma.prodenta.repository.modules.sexe.impl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Sexe_c;
import ma.prodenta.repository.modules.sexe.api.Sexe_api;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sexe_impl implements Sexe_api {
    @Override
    public List<Sexe_c> findAll() throws Exception {
        String requete= """
                select * from sexe;
                """;
        List<Sexe_c>liste_sexe=new ArrayList<>();
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Sexe_c sexe=new Sexe_c();
                sexe.setId(rs.getInt("idsexe"));
                sexe.setLibelle(rs.getString("libelle"));
                liste_sexe.add(sexe);
            }
            return liste_sexe;
        }
    }

    @Override
    public Sexe_c findById(Integer integer) throws Exception {
        String requete= """
                select * from sexe where idsexe=?
                """;
        int id;
        Sexe_c sexe=new Sexe_c();
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete);)
        {
         ps.setInt(1,integer);
         ResultSet rs=ps.executeQuery();
         if(rs.next()){
             sexe.setId(rs.getInt("idsexe"));
             sexe.setLibelle(rs.getString("libelle"));
         }
         return sexe;
        }
    }

    @Override
    public boolean create(Sexe_c objet) throws SQLException, IOException {
        return false;
    }

    @Override
    public void update(Sexe_c objet) throws SQLException, IOException, Exception {

    }

    @Override
    public boolean delete(Sexe_c objet) throws SQLException, Exception {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException, Exception {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }


    @Override
    public Sexe_c findBylibelle(String libelle) {
        String requete= """
                select idsexe from sexe where libelle like ?;
                """;
        int id=0;
        Sexe_c sexe=new Sexe_c();
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement stmt=conn.prepareStatement(requete);)
        {
            stmt.setString(1,"%"+libelle+"%");
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                sexe.setId(rs.getInt("idsexe"));
                sexe.setLibelle("libelle");
            }
            return sexe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Sexe_c map_to_sexe(ResultSet rs) throws SQLException {
        if(rs==null){
            return null;
        }
        Sexe_c sexe= new Sexe_c();
        sexe.setId(rs.getInt("idsexe"));
        sexe.setLibelle(rs.getString("libelle"));
        return sexe;
    }
    @Override
    public Integer get_last_id() {
        String sql = "SELECT MAX(idsexe) AS last_id FROM sexe";
        Integer lastId = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("last_id");

                // Si la table est vide → wasNull = true
                if (rs.wasNull()) {
                    lastId = null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lastId;
    }

}
