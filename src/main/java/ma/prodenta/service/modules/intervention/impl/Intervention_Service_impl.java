package ma.prodenta.service.modules.intervention.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.*;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.repository.modules.statut_consultation.impl.Statut_consultation_impl;
import ma.prodenta.service.modules.intervention.api.Intervention_Service_api;
import java.util.List;
import ma.prodenta.repository.modules.intervention_medcin.impl.Intervention_impl;
import ma.prodenta.entities.En.Intervention;

public class Intervention_Service_impl implements Intervention_Service_api {
    @Override
    public void ajouterInterventionAConsultation(int idConsultation, int idActe, int numeroDent, int prixPatient) throws Exception {
        Intervention_impl intervention_impl = Application_contexte.getInterventionRepository();
        Acte_impl acte_impl = Application_contexte.getActeRepository();
        ConsultationDaoimpl consultationDaoimpl = Application_contexte.getConsultationRepository();
        Statut_consultation_impl statutRepository = Application_contexte.getStatutRepository();

        Acte acte=new Acte();
        Consultation consultation = new Consultation();
        Intervention intervention = new Intervention();

        if(idConsultation <=0 || idActe <=0 || numeroDent <=0  ){
            throw new Exception("les informations des parametres sont invalides");
        }
        if (numeroDent >32){
            throw new Exception ("le numero de dents saisie est invalides");
        }
        acte=acte_impl.findById(idActe);
        if(acte==null){
            throw new Exception("Le acte n'existe pas");
        }
        consultation=consultationDaoimpl.findById(idConsultation);
        if(consultation==null) {
            throw new Exception("La consultation n'existe pas");
        }

        if (consultation.getIdStatut()!=statutRepository.findBYnom("En cours").getId()) {
            throw new Exception("impossible d'ajouter une intervention a une consultation pas en cours  ");
        }

        if(prixPatient==-1){
            prixPatient= (int) acte.getPrix_de_base();
        }
        intervention.setId_consultation(idConsultation);
        intervention.setActe(acte);
        intervention.setNumero_dent(numeroDent);
        intervention.setPrix_patient(prixPatient);
        boolean flag=intervention_impl.create(intervention);
        if(!flag){
            throw new Exception("erreur lors de la creation de l'intervention");
        }

        System.out.println("creation avec success de l'objet intervention ");
    }

    @Override
    public void verifierConsultationActive(int idConsultation) throws Exception {
        ConsultationDaoimpl consultationDaoimpl=Application_contexte.getConsultationRepository();
        Statut_consultation_impl stautConsultation=Application_contexte.getStatutRepository();
        if (idConsultation<=0) {
            throw new Exception("l'id de la consultation est invalide ");
        }
        Consultation consultation=consultationDaoimpl.findById(idConsultation);
        if (consultation==null) {
            throw new Exception("la consultation n'existe pas dans la base ");
        }
        if(consultation.getIdStatut()!=stautConsultation.findBYnom("En cours").getId()){
            throw new Exception("la consultation n'est pas en cours ");
        }
        System.out.println("l'ajout de l'intervention est valide ");
    }

    @Override
    public int calculerCoutTotalConsultation(int idConsultation) throws Exception {
        if (idConsultation<=0) {
            throw new Exception("l'id de la consultation est invalide");
        }
        int total = 0;
        Intervention_impl intervention_impl=Application_contexte.getInterventionRepository();
        ConsultationDaoimpl consultationDaoimpl=Application_contexte.getConsultationRepository();
        Consultation consultation=new Consultation();
        consultation=consultationDaoimpl.findById(idConsultation);
        if(consultation==null) {
            throw new Exception("la consultation n'existe pas");
        }
        List<Intervention> interventions = intervention_impl.interventions_par_consultation(consultation);

        for (Intervention intervention1 : interventions) {
            total += intervention1.getPrix_patient();
        }
        return total;
    }

}
