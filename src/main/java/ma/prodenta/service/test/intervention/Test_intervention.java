package ma.prodenta.service.test.intervention;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.service.modules.intervention.impl.Intervention_Service_impl;

public class Test_intervention {
    public static void main(String[] args) {

        Intervention_Service_impl interventionService = new Intervention_Service_impl();

        try {
            ConsultationDaoimpl consultationDaoimpl= Application_contexte.getConsultationRepository();
            int idConsultation = 25;
            int idActe =11 ;
            int numeroDent = 12;
            int prixPatient = -1;

            interventionService.verifierConsultationActive(idConsultation);

            interventionService.ajouterInterventionAConsultation(idConsultation, idActe, numeroDent, prixPatient);

            int total = interventionService.calculerCoutTotalConsultation(idConsultation);

            System.out.println("Cout total de la consultation : " + total);

            System.out.println("TEST INTERVENTION TERMINÉ AVEC SUCCÈS");

        } catch (Exception e) {
            System.err.println("ERREUR TEST INTERVENTION");
            e.printStackTrace();
        }
    }
}

