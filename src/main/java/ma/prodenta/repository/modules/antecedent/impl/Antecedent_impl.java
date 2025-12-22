package ma.prodenta.repository.modules.antecedent.impl;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.common.Connextion_db;
import ma.prodenta.repository.modules.antecedent.api.Antecedent_api;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;

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
        String requete = """
            INSERT INTO antecedent (nom, categorie, idrisque)
            VALUES (?, ?, ?)
        """;

        try (
                Connection con = SessionFactory.getInstance().getConnection();
                PreparedStatement prp = con.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)
        ) {
            prp.setString(1, objet.getNom());
            prp.setString(2, objet.getCategorie());

            int id_risque;
            try {
                id_risque = map_to_int(objet.getNiveauRisque());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            prp.setInt(3, id_risque);

            int nombre_lignes = prp.executeUpdate();

            if (nombre_lignes == 0) return false;

            // Récupérer l'ID généré par MySQL
            ResultSet rs = prp.getGeneratedKeys();
            if (rs.next()) {
                objet.setIdAntecedent(rs.getInt(1));
            }

            return true;
        }
    }

    @Override
    public boolean create(Patient patient) throws SQLException {
        List<Antecedent> listeAntecedents = patient.getAntecedents();
        String requetePatient = """
        INSERT INTO patient
        (nom, datenaissance, adresse, telephone, idsexe, idassurance, prenom, email)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (
                Connection con = SessionFactory.getInstance().getConnection();
                PreparedStatement prp = con.prepareStatement(requetePatient, Statement.RETURN_GENERATED_KEYS)
        ) {
            int id_sexe = patient.getSexe() == Sexe.Homme ? 1 : 2;
            int id_assurance = switch (patient.getAssurance()) {
                case CNOPS -> 1;
                case CNSS -> 2;
                case RAMED -> 3;
                default -> 4;
            };

            prp.setString(1, patient.getNom());
            prp.setDate(2, Date.valueOf(patient.getDateNaissance()));
            prp.setString(3, patient.getAdresse());
            prp.setString(4, patient.getTelephone());
            prp.setInt(5, id_sexe);
            prp.setInt(6, id_assurance);
            prp.setString(7, patient.getPrenom());
            prp.setString(8, patient.getEmail());

            int lignes = prp.executeUpdate();
            if (lignes == 0) return false;

            // récupérer l'ID généré par MySQL
            try (ResultSet rs = prp.getGeneratedKeys()) {
                if (rs.next()) {
                    patient.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Échec de la récupération de l'ID du patient.");
                }
            }

            // insérer les antécédents liés
            if (listeAntecedents != null && !listeAntecedents.isEmpty()) {
                Antecedent_patient_impl ap = new Antecedent_patient_impl();
                return ap.create(patient); // utilise maintenant l'ID généré
            }

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
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
