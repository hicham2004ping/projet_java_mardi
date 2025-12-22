package ma.prodenta.service.modules.prescription.api;

import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Prescription;
import java.sql.SQLException;
import java.util.List;

public interface Prescription_Service_api {
    List<Prescription> findByOrdonnance(Ordonnance ordonnance) throws SQLException;
    boolean existsSimilarPrescription(int idOrd, int idMed, String frequence) throws SQLException;
    double averageDurationByOrdonnance(int idOrd) throws SQLException;
    boolean deleteIfShortTerm(int idPr, int maxDays) throws SQLException;
    boolean prolongerPrescription(int idPr, int joursSupp) throws SQLException;

}
