package ma.prodenta.service.test.consultation;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.modules.RendezVous.fileBase_implementation.RDVDAOImpl;
import ma.prodenta.service.modules.consultation.impl.Consultation_service_impl;

import java.sql.Time;
import java.util.Date;

public class Consultatino {
    public static void main(String[] args) {
        Consultation_service_impl consultationService = new Consultation_service_impl();

        try {
            RDVDAOImpl rdv= Application_contexte.getRDVRepository();
            RDV rendez=new RDV();


            int idPatient = 80;
            int idDossier = 52;
            int idMedecin = 1;

            rendez.setMotif("juste pour le test ");
            rendez.setNoteMedecin("tout ira miux demain ");
            rendez.setDateRDV(new Date());
            rendez.setHeure(Time.valueOf("08:50:00"));
            rendez.setIddossier(idDossier);
            rdv.create(rendez);
            int idRdv = rendez.getIdRDV();

            consultationService.demarerConsultation(idDossier, idMedecin, idRdv, "Observation de test depuis main");


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

