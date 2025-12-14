package ma.prodenta.repository.modules.CabinetMedical.api;

import ma.prodenta.entities.En.CabinetMedical;
import ma.prodenta.repository.common.CrudRepository;

import java.io.IOException;
import java.sql.SQLException;

public interface CabinetMedicaleDao extends CrudRepository<CabinetMedical, Integer> {

    boolean create(CabinetMedical cab) throws SQLException, IOException;

    // ---------------------------
    // DELETE
    // ---------------------------
    boolean delete(CabinetMedical cab) throws Exception;
}
