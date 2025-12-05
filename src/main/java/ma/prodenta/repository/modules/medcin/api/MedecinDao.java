package ma.prodenta.repository.modules.medcin.api;

import ma.prodenta.entities.En.Medecin;

import java.util.List;
import java.util.Optional;

public interface MedecinDao {

    List<Medecin> findAll() throws Exception;

    Optional<Medecin> findById(int idUser) throws Exception;

    Medecin save(Medecin medecin) throws Exception;

    void update(Medecin medecin) throws Exception;

    void delete(int idUser) throws Exception;
}
