package ma.prodenta.service.test.consultation;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.service.modules.consultation.impl.Consultation_service_impl;

public class Consultatino {
    public static void main(String[] args) {
        Consultation_service_impl consultationService = new Consultation_service_impl();

        try {
            int idPatient = 73;
            int idDossier = 45;
            int idMedecin = 1;
            int idRdv = 5;
            int idConsultation = 25;

            //consultationService.demarerConsultation(idDossier, idMedecin, idRdv, "Observation de test depuis main");


            Consultation consultationActive = consultationService.getConsultationActiveDuPatient(idPatient);

            System.out.println("Consultation active ID : " + consultationActive.getIdConsult());


            consultationService.cloturerConsultation(consultationActive.getIdConsult());

            System.out.println("Consultation cloturer  avec succès");

        } catch (Exception e) {
            System.err.println("ERREUR TEST CONSULTATION");
            e.printStackTrace();
        }

    }
}

