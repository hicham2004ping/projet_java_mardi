package ma.prodenta.repository.modules.user.implementation;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.user.api.user_dao;
import ma.prodenta.Connextion_db;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data @NoArgsConstructor
public class UserImpl implements user_dao {
    @Override
    public Utilisateur getUser(String username,String password ) throws RuntimeException, SQLException {
        Connextion_db conn;
        Utilisateur utilisateur=null;
        try {
            conn = new Connextion_db();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            String dbUrl = conn.getUrl();
            String  dbUser = conn.getUsername();
            String dbPassword = conn.getPassword();
            Connection myconn= DriverManager.getConnection(dbUrl,dbUser,dbPassword);
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
            LocalDate lastLoginDate=rs.getDate("lastLoginDate").toLocalDate();
            int idRole=rs.getInt("idrole");
             utilisateur =new Utilisateur(idUser,nom,email,adresse,cin,tel,idSexe,login,motdepasse,dateNaissance,lastLoginDate,idRole);
        }
        return utilisateur;
    }

    @Override
    public List<Utilisateur> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Utilisateur findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public void create(Utilisateur patient) {

    }

    @Override
    public void update(Utilisateur patient) {

    }

    @Override
    public void delete(Utilisateur patient) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}