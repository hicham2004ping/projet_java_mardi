package ma.prodenta.repository.modules.patient.api;

import ma.prodenta.entities.En.Patient;
import java.util.List;

public interface PatientDao {
    void ajouter(Patient patient) throws Exception;
    void mettreAJour(Patient patient) throws Exception;
    void supprimer(int idPatient) throws Exception;
    Patient trouverParId(int idPatient) throws Exception;
    List<Patient> trouverTous() throws Exception;
}
