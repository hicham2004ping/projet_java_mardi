package ma.prodenta.service.modules.consultation.api;
import ma.prodenta.entities.En.Consultation;
import java.util.List;

public interface Consultation_service_impl {
    void cloturerConsultation(int idConsultation) throws Exception;
    Consultation getConsultationActiveDuPatient(int idPatient) throws Exception;
    void demarerConsultation(int idDossier,int idMedecin,int idRdv,String observation) throws Exception;
}
