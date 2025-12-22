package ma.prodenta.repository.test_repository;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.Intervention;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.repository.modules.intervention_medcin.impl.Intervention_impl;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.repository.modules.intervention_medcin.impl.Intervention_impl;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import java.util.Date;

public class Test_Scenario_consultation {
    public  void creation(){
        boolean flag1;
        Acte_impl acte_impl= Application_contexte.getActeRepository();
        Intervention_impl intervention_impl= Application_contexte.getInterventionRepository();
        ConsultationDaoimpl consultationDaoimpl= Application_contexte.getConsultationRepository();
        Consultation consultation=new Consultation();
        Acte acte=new Acte();
        Intervention intervention=new Intervention();
        try{
            //creation de consultation
            consultation.setDateConsult(new Date());
            consultation.setId_rdv(1);
            consultation.setObservationMedecin("lhamdoulilah");
            consultation.setIdStatut(1);
            consultation.setId_medecin(1);
            consultation.setIdDossier(11);
            boolean flag=consultationDaoimpl.create(consultation);
            if(flag){
                System.out.println("la consultation a ete creer avec success");
                acte= acte_impl.findById(1);
                int id1=consultationDaoimpl.last_id();
                intervention.setId_consultation(consultationDaoimpl.findById(id1).getIdConsult());
                intervention.setPrix_patient(-1);
                intervention.setActe(acte);
                intervention.setNumero_dent(10);
                flag1=intervention_impl.create(intervention);
                if(flag1){
                    System.out.println("creation avec success de l'intervention ");
                    int id=intervention_impl.get_last_id();
                    System.out.println("l'id de l'intervention est "+intervention_impl.findById(id));
                }
                else{
                    System.out.println("erreur lors de la creation du l'intervention");
                }
            }
            else{
                System.out.println("echec lors de la creation du consultation ");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void lecture(){

        ConsultationDaoimpl consultationDaoimpl = Application_contexte.getConsultationRepository();
        try {
            System.out.println("Liste des consultations :");
            for (Consultation c : consultationDaoimpl.findAll()) {
                System.out.println("ID : " + c.getIdConsult() +
                        ", Date : " + c.getDateConsult() +
                        ", Observation : " + c.getObservationMedecin());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture des consultations : " + e.getMessage());
        }
    }


    public void update(){
        ConsultationDaoimpl consultationDaoimpl = Application_contexte.getConsultationRepository();
        try {
            int idToUpdate = 7;
            Consultation c = consultationDaoimpl.findById(idToUpdate);
            if (c != null) {
                c.setObservationMedecin("Observation trouver avec l'id "+idToUpdate);
                c.setObservationMedecin("alhadmoulilah ");
                consultationDaoimpl.update(c);
                System.out.println("l'observation du consultation est "+c.getObservationMedecin());
                System.out.println("Consultation mise à jour avec succès : " + c.getObservationMedecin());
            } else {
                System.out.println("Consultation introuvable avec l'ID " + idToUpdate);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }
    public void suppression(){
        ConsultationDaoimpl consultationDaoimpl = Application_contexte.getConsultationRepository();
        try {
            int idToDelete = consultationDaoimpl.last_id();
            boolean deleted = consultationDaoimpl.delete(consultationDaoimpl.findById(idToDelete));
            if (deleted) {
                System.out.println("Consultation supprimée avec succès.");
            } else {
                System.out.println("Échec de la suppression de la consultation.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        Test_Scenario_consultation t=new Test_Scenario_consultation();
        /*
        t.creation();
        t.suppression();
        */
    }
}
