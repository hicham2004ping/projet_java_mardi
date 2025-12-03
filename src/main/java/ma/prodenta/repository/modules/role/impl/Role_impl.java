package ma.prodenta.repository.modules.role.impl;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Role;
import ma.prodenta.repository.modules.role.api.Role_api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ma.prodenta.config.SessionFactory;
public class Role_impl implements Role_api {
    @Override
    public Role find_by_nom(String nom) {
        String requete= """
                select * from role where libelle like ?
                """;
        try(Connection conn=SessionFactory.getInstance().getConnection();
            PreparedStatement pst=conn.prepareStatement(requete);){
            pst.setString(1,"%"+nom+"%");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Role> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Role findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public boolean create(Role objet) throws SQLException, IOException {
        return false;
    }

    @Override
    public void update(Role objet) {

    }

    @Override
    public boolean delete(Role objet) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}
