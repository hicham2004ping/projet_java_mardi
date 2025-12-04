package ma.prodenta.repository.modules.forme.api;

import ma.prodenta.entities.En.Forme;
import ma.prodenta.repository.common.CrudRepository;

import java.sql.SQLException;

public interface Forme_api extends CrudRepository<Forme,Integer> {
    public int total_nombre_de_format() throws SQLException;
    public int get_last_id() throws SQLException;
}
