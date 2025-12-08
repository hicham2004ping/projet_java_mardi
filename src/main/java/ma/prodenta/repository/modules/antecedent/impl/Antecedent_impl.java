package ma.prodenta.repository.modules.antecedent.impl;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.repository.common.Connextion_db;
import ma.prodenta.repository.modules.antecedent.api.Antecedent_api;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.config.SessionFactory;

public class Antecedent_impl implements Antecedent_api {

    Connextion_db connetion;

    public int id_par_nom(String nom) throws SQLException, ClassNotFoundException ,IOException{
        int id=0;
        try(Connection conn = SessionFactory.getInstance().getConnection()){
            PreparedStatement prs=conn.prepareStatement("select idantecedent from Antecedent where nom like ? ");
            prs.setString(1, '%' + nom + '%');
            ResultSet rs=prs.executeQuery();
            if(rs.next()){
                id=rs.getInt("idAntecedent");
            }
        }
        return id;
    }

    @Override
    public NiveauRisque map_to_enum(int id) throws Exception, SQLException {
        return switch (id) {
            case 1 -> NiveauRisque.Dangereux;
            case 2 -> NiveauRisque.Trèsdangereux;
            case 3 -> NiveauRisque.Modéré;
            default -> NiveauRisque.Faible;
        };
    }

    @Override
    public int map_to_int(NiveauRisque n) throws Exception, SQLException {
        return switch (n) {
            case Dangereux -> 1;
            case Trèsdangereux -> 2;
            case Modéré -> 3;
            default -> 4;
        };
    }

    @Override
    public List<Antecedent> findAll() throws Exception,IOException ,SQLException {
        List<Antecedent> list = new ArrayList<>();
        Antecedent antecedent = new Antecedent();
        try(Connection conn = SessionFactory.getInstance().getConnection()){
            PreparedStatement pst=conn.prepareStatement("select * from antecedent");
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                antecedent.setIdAntecedent(rs.getInt("idantecedent"));
                antecedent.setCategorie(rs.getString("categorie"));
                antecedent.setNom(rs.getString("nom"));
                int id_risque;
                id_risque=rs.getInt("idrisque");
                if(id_risque==1){
                    antecedent.setNiveauRisque(NiveauRisque.Dangereux);
                }
                else if(id_risque==2){
                    antecedent.setNiveauRisque(NiveauRisque.Trèsdangereux);
                }
                else if(id_risque==3){
                    antecedent.setNiveauRisque(NiveauRisque.Modéré);
                }
                else{
                    antecedent.setNiveauRisque(NiveauRisque.Faible);
                }
                list.add(antecedent);
                antecedent=new Antecedent();
            }
            return list;
        }
    }

    @Override
    public Antecedent findById(Integer id) throws Exception {
        Antecedent antecedent = null;
        try (Connection conn = SessionFactory.getInstance().getConnection()) {

            PreparedStatement pst =
                    conn.prepareStatement("SELECT * FROM antecedent WHERE idantecedent = ?");
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                antecedent = new Antecedent();
                antecedent.setIdAntecedent(rs.getInt("idantecedent"));
                antecedent.setNom(rs.getString("nom"));
                antecedent.setCategorie(rs.getString("categorie"));
                int id_risque = rs.getInt("idrisque");
                if (id_risque==1){
                    antecedent.setNiveauRisque(NiveauRisque.Dangereux);
                }
                else if(id_risque==2){
                    antecedent.setNiveauRisque(NiveauRisque.Trèsdangereux);
                }
                else if(id_risque==3){
                    antecedent.setNiveauRisque(NiveauRisque.Modéré);
                }
                else{
                    antecedent.setNiveauRisque(NiveauRisque.Faible);
                }
            }
        }

        return antecedent;

    }

    @Override
    public boolean create(Antecedent objet) throws SQLException, IOException {
        int id = 0;
        try {
            id = this.get_last_id()+1;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try(Connection conn = SessionFactory.getInstance().getConnection()){
            int id_risque=0;
            PreparedStatement stmt=conn.prepareStatement("insert into antecedent values(?,?,?,?)");
            stmt.setInt(1,id);
            stmt.setString(2,objet.getNom());
            stmt.setString(3,objet.getCategorie());
            if(objet.getNiveauRisque().name().equals("Faible")){
                id_risque=4;
            }
            else if (objet.getNiveauRisque().name().equals("Modéré")){
                id_risque=3;
            }
            else if(objet.getNiveauRisque().name().equals("Trèsdangereux")){
                id_risque=2;
            }
            else{
                id_risque=1;
            }
            stmt.setInt(4,id_risque);
            int rs=stmt.executeUpdate();
            return rs>0;
        }
    }

    @Override
    public void update(Antecedent objet) {
        try (Connection conn = SessionFactory.getInstance().getConnection()) {
            PreparedStatement pst = conn.prepareStatement(
                    "UPDATE antecedent SET nom = ? , categorie = ? , idrisque = ? WHERE idantecedent = ?");
            pst.setString(1, objet.getNom());
            pst.setString(2, objet.getCategorie());
            try {
                pst.setInt(3, map_to_int(objet.getNiveauRisque()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            pst.setInt(4, objet.getIdAntecedent());
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean delete(Antecedent objet) throws SQLException {
        return deleteById(objet.getIdAntecedent());
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException {
        try (Connection conn = SessionFactory.getInstance().getConnection()) {

            PreparedStatement pst =
                    conn.prepareStatement("DELETE FROM antecedent WHERE idantecedent = ?");

            pst.setInt(1, integer);

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public List<String> find_all_names() throws SQLException, ClassNotFoundException, IOException{
        List<String> list=new ArrayList<>();
        try(Connection conn = SessionFactory.getInstance().getConnection()){
            PreparedStatement stmt=conn.prepareStatement("select nom from Antecedent");
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("nom"));
            }
        }
        return list;
    }

    public int get_last_id() throws SQLException, ClassNotFoundException, IOException{
        int id=0;
        try(Connection conn = SessionFactory.getInstance().getConnection()){
            PreparedStatement stmt=conn.prepareStatement("select max(idantecedent) from Antecedent");
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                id= rs.getInt(1);
            }
            return id ;
        }
    }
    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

}
