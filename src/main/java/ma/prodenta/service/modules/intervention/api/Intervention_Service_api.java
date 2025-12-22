package ma.prodenta.service.modules.intervention.api;

import ma.prodenta.entities.En.Intervention;

import java.util.List;

public interface Intervention_Service_api {
    void ajouterInterventionAConsultation(int idConsultation, int idActe, int numeroDent, int prixPatient) throws Exception;
    void verifierConsultationActive(int idConsultation) throws Exception;
    int calculerCoutTotalConsultation(int idConsultation) throws Exception;
}
