package ma.prodenta.repository.modules.forme.impl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Forme;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.forme.api.Forme_api;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Forme_impl implements Forme_api {

    @Override
    public int total_nombre_de_format() throws SQLException {
        String requete = "SELECT COUNT(*) FROM forme";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(requete);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public int get_last_id()  throws SQLException {
        String requete = """
                select max(idforme) from forme
                """;
        int numero=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete);)
        {
         ResultSet rs=ps.executeQuery();
         if(rs.next()){
             numero=rs.getInt(1);
         }
         return numero;
        }
    }

    @Override
    public Forme map_resultset_to_forme(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public List<Forme> findAll() throws SQLException {
        List<Forme> formes = new ArrayList<>();
        String requete = "SELECT * FROM forme";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(requete);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Forme f = new Forme();
                f.setId(rs.getInt("idforme"));
                f.setLibelle(rs.getString("libelle"));
                formes.add(f);
            }
        }
        return formes;
    }


    @Override
    public Forme findById(Integer integer) throws Exception {
      String  requete= """
                select * from forme where idforme=?
                """;
      Forme forme=new Forme();
      try(Connection conn=SessionFactory.getInstance().getConnection();
      PreparedStatement ps=conn.prepareStatement(requete);){
       ps.setInt(1,integer);
       ResultSet rs=ps.executeQuery();
       if(rs.next()){
           forme.setId(rs.getInt(1));
           forme.setLibelle(rs.getString(2));
       }
       return forme;
      }
    }

    @Override
    public boolean create(Forme objet) throws SQLException, IOException {
        String requete= """
                insert into forme (libelle) values (?)
                """;
        int last_id=get_last_id()+1;
        int n=0;
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement pst= conn.prepareStatement(requete);)
        {
                pst.setString(1, objet.getLibelle());
                n=pst.executeUpdate();
        }
        return n>0;
    }

    @Override
    public void update(Forme objet) throws SQLException, IOException {
        String requete = "UPDATE forme SET libelle = ? WHERE idforme = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(requete)) {
            ps.setString(1, objet.getLibelle());
            ps.setInt(2, objet.getId());
            ps.executeUpdate();
        }

    }


    @Override
    public boolean delete(Forme objet) throws SQLException, Exception {
        return deleteById(objet.getId());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String requete = "DELETE FROM forme WHERE idForme = ?";
        int n;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(requete)) {
            ps.setInt(1, id);
            n = ps.executeUpdate();
        }
        return n > 0;
    }
    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
    public Forme findbynom(String nom) throws SQLException {
        String requete= """
                select * from forme where libelle like ?
                """;
        Forme forme=new Forme();
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ps.setString(1, "%"+nom+"%");
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                forme.setLibelle(rs.getString("libelle"));
                forme.setId(rs.getInt("idforme"));
            }
            return forme;
        }
    }
}
