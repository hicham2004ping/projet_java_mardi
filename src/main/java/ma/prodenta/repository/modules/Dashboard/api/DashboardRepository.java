
package ma.prodenta.repository.modules.Dashboard.api;

import ma.prodenta.entities.En.Dashboard;
import java.util.Date;
import java.util.List;

public interface DashboardRepository {
    Dashboard findById(Integer id) throws Exception;
    List<Dashboard> findAll() throws Exception;
    Dashboard findByPeriode(Date dateDebut, Date dateFin) throws Exception;
    Dashboard save(Dashboard dash) throws Exception;
    Dashboard update(Dashboard dash) throws Exception;
    void delete(Integer id) throws Exception;
}
