package ma.prodenta.repository.modules.fieldattente;

import ma.prodenta.entities.En.FileAttente;
import java.time.LocalDate;
import java.util.List;

public interface FileAttenteDao {
    FileAttente findById(Integer idFileAttente) throws Exception;
    List<FileAttente> findByDateFile(LocalDate dateFile) throws Exception;
    List<FileAttente> findByIdDossier(Integer idDossier)  throws Exception;
    List<FileAttente> findTodayQueue()  throws Exception;
    void create(FileAttente fileAttente)  throws Exception;
    void update(FileAttente fileAttente)   throws Exception;
    void delete(Integer idFileAttente)  throws Exception;
    void deleteByDateFile(LocalDate dateFile)  throws Exception;
    Integer getLastPosition(LocalDate dateFile)  throws Exception;
}
