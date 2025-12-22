package ma.prodenta.service.modules.Revenus.api;

import ma.prodenta.entities.En.Revenus;

import java.util.List;

public interface RevenusService {

    Revenus getRevenuById(Long id) throws Exception;

    boolean createRevenu(Revenus revenu) throws Exception;


    List<Revenus> getAllRevenus() throws Exception;


    void updateRevenu(Revenus revenu) throws Exception;

    boolean deleteRevenuById(Long id) throws Exception;
}
