package ma.prodenta.repository.modules.user.implementation;
import com.mysql.cj.Session;
import lombok.Data;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.user.api.user_dao;
import ma.prodenta.repository.common.Connextion_db;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class UserImpl implements user_dao {
    @Override
    public Utilisateur getUser(String username,String password ) throws RuntimeException, SQLException {
        Utilisateur utilisateur=null;
        try(Connection myconn= SessionFactory.getInstance().getConnection()){

            PreparedStatement pstmt = myconn.prepareStatement("select * from utilisateur where login=? and motdepasse=?");
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idUser = rs.getInt("idUser");
                String nom=rs.getString("nom");
                String email=rs.getString("email");
                String adresse=rs.getString("adresse");
                String cin=rs.getString("cin");
                String tel=rs.getString("tel");
                int idSexe=rs.getInt("idsexe");
                String login=rs.getString("login");
                String motdepasse=rs.getString("motdepasse");
                LocalDate dateNaissance=rs.getDate("dateNaissance").toLocalDate();
                Timestamp ts=rs.getTimestamp("lastLoginDate");
                LocalDateTime ldt=(ts != null) ? rs.getTimestamp("lastLoginDate").toLocalDateTime() :null;
                int idRole=rs.getInt("idrole");
                utilisateur =new Utilisateur(idUser,nom,email,adresse,cin,tel,idSexe,login,motdepasse,dateNaissance,ldt,idRole);
            }
        }
        return utilisateur;
    }

    @Override
    public List<Utilisateur> findAll() throws SQLException,IOException {
        List<Utilisateur>users=new ArrayList<>();
        try(Connection myconn= SessionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = myconn.prepareStatement("select * from utilisateur");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id=rs.getInt("idUser");
                String nom=rs.getString("nom");
                String email=rs.getString("email");
                String adresse=rs.getString("adresse");
                String cin=rs.getString("cin");
                String tel=rs.getString("tel");
                int idSexe=rs.getInt("idsexe");
                String login=rs.getString("login");
                String motdepasse=rs.getString("motdepasse");
                LocalDate dateNaissance=rs.getDate("dateNaissance").toLocalDate();
                Timestamp ts=rs.getTimestamp("lastLoginDate");
                LocalDateTime ldt=(ts != null) ? ts.toLocalDateTime() :null;
                int idRole=rs.getInt("idrole");
                Utilisateur utilisateur = new Utilisateur(id,nom,email,adresse,cin,tel,idSexe,login,motdepasse,dateNaissance,ldt,idRole);
                users.add(utilisateur);
            }

        }
        return users;
    }

    @Override
    public Utilisateur findById(Integer integer) throws Exception,SQLException {
              Utilisateur utilisateur=null;
              try(Connection conn=SessionFactory.getInstance().getConnection()){
                    PreparedStatement stmt = conn.prepareStatement("select * from utilisateur where idUser=?");
                    stmt.setInt(1,integer);
                   ResultSet rs =stmt.executeQuery();
                   if(rs.next()){
                       int id=rs.getInt("idUser");
                       String nom=rs.getString("nom");
                       String email=rs.getString("email");
                       String adresse=rs.getString("adresse");
                       String cin=rs.getString("cin");
                       String tel=rs.getString("tel");
                       int idSexe=rs.getInt("idsexe");
                       String login=rs.getString("login");
                       String motdepasse=rs.getString("motdepasse");
                       LocalDate dateNaissance=rs.getDate("dateNaissance").toLocalDate();
                       Timestamp ts=rs.getTimestamp("lastLoginDate");
                       LocalDateTime lastlogin=(ts !=null)? ts.toLocalDateTime():null;
                       int idRole=rs.getInt("idrole");
                        utilisateur = new Utilisateur(id,nom,email,adresse,cin,tel,idSexe,login,motdepasse,dateNaissance,lastlogin,idRole);
                   }
              }
              return utilisateur;
    }

    @Override
    public boolean create(Utilisateur user) throws SQLException, IOException {
        int utilisateur_id=user.getIdUser();
        Utilisateur user_test=null;
      //  UserImpl user_sup=new UserImpl();
        try{
            user_test=this.findById(utilisateur_id);
            if (user_test!=null){
                return false;
            }
        }
        catch (Exception e){
            System.out.println("l'utilisateur invalide");
            return false;
        }
        try(Connection conn=SessionFactory.getInstance().getConnection()){
            int id=user.getIdUser();
            String nom=user.getNom();
            String email=user.getEmail();
            String adresse=user.getAdresse();
            String cin=user.getCin();
            String tel=user.getTel();
            int id_sexe=user.getIdSexe();
            String login=user.getLogin();
            String motdepasse=user.getMotdepasse();
            LocalDate dateNaissance=user.getDateNaissance();
            LocalDateTime lastLoginDate=null;
            int idRole=user.getIdRole();
            PreparedStatement prp=conn.prepareStatement("insert into utilisateur values(?,?,?,?,?,?,?,?,?,?,?,?) ");
            prp.setInt(1,id);
            prp.setString(2,nom);
            prp.setString(3,email);
            prp.setString(4,adresse);
            prp.setString(5,cin);
            prp.setString(6,tel);
            prp.setInt(7,id_sexe);
            prp.setString(8,login);
            prp.setString(9,motdepasse);
            System.out.println("l'id de l'utilisateur est "+id);
            prp.setDate(10,java.sql.Date.valueOf(dateNaissance));
            System.out.println("la date de naissance est "+dateNaissance);
            prp.setObject(11,null);
            prp.setInt(12,idRole);
            prp.execute();
        }
        return true;
    }
    @Override
    public void update(Utilisateur objet) {
    }

    @Override
    public boolean delete(Utilisateur objet) throws SQLException {
       int  user_id=objet.getIdUser();
       Utilisateur user1=null;
       try(Connection conn=SessionFactory.getInstance().getConnection()){
           PreparedStatement prp=conn.prepareStatement("delete from utilisateur where idUser=?");
           prp.setInt(1,user_id);
        int numerolignes= prp.executeUpdate();
        return numerolignes>0;
       }
       catch(Exception e) {
           System.out.println("l'utilisateur invalide");
           return false;
       }
    }

    @Override
    public boolean deleteById(Integer integer)throws SQLException{
        try(Connection conn=SessionFactory.getInstance().getConnection()){
            PreparedStatement stmt=conn.prepareStatement("delete from utilisateur where idUser=?");
            stmt.setInt(1,integer);
           int nombre_lignes= stmt.executeUpdate();
           return nombre_lignes>0;
        }
        catch(Exception e){
            System.out.println("l'utilisateur invalide");
            return false;
        }
    }
    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    public  Utilisateur mapUtilisateur(ResultSet rs) throws SQLException {
        int id = rs.getInt("idUser");
        String nom = rs.getString("nom");
        String email = rs.getString("email");
        String adresse = rs.getString("adresse");
        String cin = rs.getString("cin");
        String tel = rs.getString("tel");
        int idSexe = rs.getInt("idsexe");
        String login = rs.getString("login");
        String motdepasse = rs.getString("motdepasse");

        LocalDate dateNaissance = null;
        Date date = rs.getDate("dateNaissance");
        if (date != null) {
            dateNaissance = date.toLocalDate();
        }

        LocalDateTime lastLogin = null;
        Timestamp ts = rs.getTimestamp("lastLoginDate");
        if (ts != null) {
            lastLogin = ts.toLocalDateTime();
        }
        int idRole = rs.getInt("idrole");
        return new Utilisateur(
                id,
                nom,
                email,
                adresse,
                cin,
                tel,
                idSexe,
                login,
                motdepasse,
                dateNaissance,
                lastLogin,
                idRole
        );
    }
    public Integer get_last_id() {
        String sql = "SELECT MAX(idUser) AS last_id FROM utilisateur";
        Integer lastId = null;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("last_id");
                if (rs.wasNull()) {  // table vide
                    lastId = null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lastId;
    }


}
