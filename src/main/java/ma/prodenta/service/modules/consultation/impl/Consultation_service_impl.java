package ma.prodenta.service.modules.consultation.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Staut_consultation;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.statut_consultation.impl.Statut_consultation_impl;

public class Consultation_service_impl implements ma.prodenta.service.modules.consultation.api.Consultation_service_impl {
    @Override
    public void cloturerConsultation(int idConsultation) throws Exception {
        Statut_consultation_impl statutRepository =Application_contexte.getStatutRepository();
        ConsultationDaoimpl consultationDaoimpl = Application_contexte.getConsultationRepository();

        Staut_consultation statut=new Staut_consultation();
        Consultation consultation = new Consultation();

        statut=statutRepository.findBYnom("Terminé");
        consultation=consultationDaoimpl.findById(idConsultation);

        if(consultation.getIdStatut()!=statutRepository.findBYnom("En cours").getId()){
            throw new Exception("impossible de cloturer une consultation qu'est pas en cours");
        }

        int id=consultation.getIdStatut();
        consultation.setIdStatut(statut.getId());
        consultationDaoimpl.update(consultation);

        if(id==consultation.getIdStatut()){
            throw new Exception("erreur lors de la mise a jour de la statut du consultation");
        }
        System.out.println("le statut de la consultation a ete mis ajour avec success !!");
    }

    @Override
    public Consultation getConsultationActiveDuPatient(int idPatient) throws Exception {
       if (idPatient<=0){
           throw new Exception("la valeur d'id est invalide");
       }

     Staut_consultation statut=new Staut_consultation();
     Consultation consultation=new Consultation();
     DossierMedical dossier=new DossierMedical();

     Patient_impl patient=Application_contexte.getPatientRepository();
     Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();
     ConsultationDaoimpl consultationDaoimpl = Application_contexte.getConsultationRepository();
     Statut_consultation_impl statutRepository=Application_contexte.getStatutRepository();

     dossier=dossier_medical_impl.find_patient(patient.findById(idPatient));
     if(dossier==null){
         throw new Exception("aucun dossier ne correspand a ce patient ");
     }
     List<Consultation>consultations=consultationDaoimpl.findByDossier(dossier.getIdDossier());
     statut=statutRepository.findBYnom("En cours");
     if (statut==null){
         throw new Exception("le statut n'existe pas");
     }

     for(Consultation c:consultations){
         if(c.getIdStatut()==statut.getId()){
             consultation=c;
             break;
         }
     }
        return consultation;
    }
    @Override
    public void demarerConsultation(int idDossier ,int idMedecin,int idRdv,String observation) throws Exception {

        if(idDossier<=0||idMedecin<=0||idRdv<=0){
            throw new Exception("merci de saisir des valeurs valide comme parametres ");
        }

        Consultation consultation=new Consultation();
        Staut_consultation statut=new Staut_consultation();

        DossierMedical dossier=new DossierMedical();

        Statut_consultation_impl statutRepository=Application_contexte.getStatutRepository();
        ConsultationDaoimpl consultationDaoimpl=Application_contexte.getConsultationRepository();
        Dossier_medical_impl dossier_medical_impl=Application_contexte.getDossierMedicalRepository();

        dossier=dossier_medical_impl.findById(idDossier);
        statut=statutRepository.findBYnom("En cours");

        if(statut==null){
            throw new Exception ("id de statut invalide ");
        }

        if (dossier==null){
            throw new Exception("id dossier invalide");
        }
        for(Consultation consultation1:consultationDaoimpl.findByDossier(dossier.getIdDossier())){
            if  (consultation1.getIdStatut()==statut.getId()){
                throw new Exception("impossible de demarer une nouvelle consultation pandant qu'une autre n'est pas encore terminer");
            }
        }

        consultation.setIdDossier(dossier.getIdDossier());
        consultation.setId_medecin(idMedecin);
        consultation.setId_rdv(idRdv);
        consultation.setObservationMedecin(observation);
        consultation.setIdStatut(statut.getId());
        consultation.setDateConsult(new Date());

        boolean flag= consultationDaoimpl.create(consultation);
        if(!flag){
            throw new Exception("erreur l'ors de la creation du consultation");
        }
        System.out.println("creation du consultation avec success");
    }
}
