package ma.prodenta.repository.modules.actes.api;

import ma.prodenta.entities.En.Acte;
import ma.prodenta.repository.common.CrudRepository;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Acte_api extends CrudRepository<Acte,Integer> {
    public int get_last_id() throws SQLException;
    public int total_actes()  throws SQLException;
    public Acte mapResultSetToActe(ResultSet resultSet) throws SQLException;
}
