package ma.prodenta.service.modules.dashboard.api;

import ma.prodenta.entities.En.Dashboard;
import java.util.Date;
import java.util.List;

public interface DashboardService {
    Dashboard findById(Integer id) throws Exception;
    List<Dashboard> findAll() throws Exception;
    Dashboard findByPeriode(Date dateDebut, Date dateFin) throws Exception;
    Dashboard save(Dashboard dashboard) throws Exception;
    Dashboard update(Dashboard dashboard) throws Exception;
    void delete(Integer id) throws Exception;
}
