package ma.prodenta.repository.modules.role.impl;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Role;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.role.api.Role_api;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.config.SessionFactory;
public class Role_impl implements Role_api {
    @Override
    public Role find_by_nom(String nom) {
        Role role = new Role();
        String requete= """
            select * from role where libelle like ?
            """;
        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1,"%"+nom+"%");
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                role.setLibelle(rs.getString("libelle"));
                role.setIdRole(rs.getInt("idRole")); // << ici, changer id_role → idRole
            }
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Role> findAll() throws Exception {

        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM role";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setIdRole(rs.getInt("idRole"));
                role.setLibelle(rs.getString("libelle"));
                roles.add(role);
            }
        }

        return roles;
    }

    @Override
    public Role findById(Integer id) throws Exception {

        Role role = null;
        String sql = "SELECT * FROM role WHERE idRole = ?";

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                role = new Role();
                role.setIdRole(rs.getInt("idRole"));
                role.setLibelle(rs.getString("libelle"));
            }
        }

        return role;
    }

    @Override
    public boolean create(Role objet) throws SQLException, IOException {
        String sql = "INSERT INTO role(libelle) VALUES(?)";

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, objet.getLibelle());

            return pst.executeUpdate() > 0;
        }

    }

    @Override
    public void update(Role objet) {
        String sql = "UPDATE role SET libelle = ? WHERE idRole = ?";

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, objet.getLibelle());
            pst.setInt(2, objet.getIdRole());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Role objet) throws SQLException {
        return deleteById(objet.getIdRole());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {

        String sql = "DELETE FROM role WHERE idRole = ?";

        try(Connection conn = SessionFactory.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}
