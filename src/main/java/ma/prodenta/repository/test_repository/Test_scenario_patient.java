package ma.prodenta.repository.test_repository;
import ma.prodenta.entities.En.*;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.medicament.fileBase_implementation.MedicamentDAOImpl;
import ma.prodenta.repository.modules.ordonnance.impl.OrdonnanceDaoImpl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.repository.modules.intervention_medcin.impl.Intervention_impl;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.repository.modules.prescription.impl.Prescription_impl;

public class Test_scenario_patient {
   private static int id_patient;

    public void creation(){
        Dossier_medical_impl d1=Application_contexte.getDossierMedicalRepository();
        Patient_impl patientRepository =Application_contexte.getPatientRepository();
        Antecedent_impl antecedent_impl=Application_contexte.getAntecedentRepository();
        ConsultationDaoimpl consultationDaoimpl=Application_contexte.getConsultationRepository();
        Intervention_impl intervention_impl=Application_contexte.getInterventionRepository();
        OrdonnanceDaoImpl ordonnanceDao=Application_contexte.getOrdonnanceRepository();
        Prescription_impl prescription_impl=Application_contexte.getPrescriptionRepository();
        MedicamentDAOImpl medicamentDAO=Application_contexte.getMedicamentRepository();

        Acte_impl acte_impl=Application_contexte.getActeRepository();
        Consultation consultation=new Consultation();
        Intervention intervention=new Intervention();
        Medicament medicament =new Medicament();
        Ordonnance ordonnance=new Ordonnance();
        Prescription prescription=new Prescription();

        Acte acte=new Acte();
        List<Antecedent> antecedents=new ArrayList<>();
        Patient p=new Patient();
        DossierMedical d=new DossierMedical();
        DossierMedical d2=new DossierMedical();

        p.setNom("chokr");
        p.setPrenom("fouad");
        p.setSexe(Sexe.Homme);
        p.setAdresse("Beirut");
        p.setAssurance(Assurance.CNSS);
        p.setTelephone("012923148");
        p.setEmail("fouad@chokr.com");
        p.setDateNaissance(LocalDate.now());

        try{
            //remplissage de la list des antecedents
            for(int i=1;i<=5;i++){
                antecedents.add(antecedent_impl.findById(i));
            }

            p.setAntecedents(antecedents);
            boolean flag=patientRepository.create(p);

            if(flag){
                System.out.println("creation avec success du patient son id est "+p.getId());
                id_patient=p.getId();
                System.out.println("la valeur de l'attribut id_patient est "+id_patient);
                //creation dossier medicale
                d.setIdPatient(p.getId());
                d.setIdMedecin(1);
                d.setDateCreation(LocalDate.now());
                boolean flag2=d1.create(d);
                //stockage dans la base
                if(flag2){

                    System.out.println("creation  du dossier avec succes");
                    d2=d1.find_patient(p);
                    System.out.println("l'id du dossier est "+d2.getIdDossier()+" il appartient au patient"+p.getNom());

                    consultation.setObservationMedecin("tout ira mieux demain");
                    consultation.setId_rdv(1);
                    consultation.setIdDossier(d2.getIdDossier());
                    consultation.setIdStatut(1);
                    consultation.setDateConsult(new Date());
                    consultation.setId_medecin(1);
                    boolean flag3=consultationDaoimpl.create(consultation);

                    //test de la creation du consultation
                    if(flag3){
                        System.out.println("creation  du consultation avec succes son id est "+consultation.getIdConsult());
                        intervention.setPrix_patient(-1);
                        intervention.setNumero_dent(10);
                        intervention.setId_consultation(consultation.getIdConsult());
                        intervention.setActe(acte_impl.findById(1));
                        boolean flag4=intervention_impl.create(intervention);
                        //test de la creation de l'intervention
                        if(flag4){
                            System.out.println("creation  du intervention avec succes");
                            //creation de l'ordonance
                            ordonnance.setIdconsultation(consultation.getIdConsult());
                            ordonnance.setIdDossier(d1.find_patient(patientRepository.findById(id_patient)).getIdDossier());
                            ordonnance.setDateOrd(LocalDate.now());
                            boolean flag5=ordonnanceDao.create(ordonnance);
                            if(flag5){
                                System.out.println("creaton avec success de l'objet ordonance");
                                medicament=medicamentDAO.findById(1);
                                prescription.setDureeEnJours(3);
                                prescription.setFrequence("2 fois par jour");
                                prescription.setQuantite(1);
                                prescription.setIdOrd(Math.toIntExact(ordonnance.getIdOrd()));
                                prescription.setIdMed(medicament.getIdMed());
                                boolean flag6=prescription_impl.create(prescription);
                                if(flag6){
                                    System.out.println("creation avec success de l'objet prescription ");
                                }
                                else{
                                    System.out.println("erreur lors de la creation du prescription");
                                }
                            }
                            else{
                                System.out.println("echec de creation de l'ordonance");
                            }
                        }
                        else{
                            System.out.println("echec lors de la creation de l'intervention ");
                        }                    }
                    else{
                        System.out.println("echec lors de la creation du consultation");
                    }
                }
                else{
                    System.out.println("echec lors du creation du dossier");
                }
            }
            else{
                System.out.println("creation avec error du patient son id est "+p.getId());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void lecture(){
        Patient_impl patientRepo = Application_contexte.getPatientRepository();
        Dossier_medical_impl dossierRepo = Application_contexte.getDossierMedicalRepository();
        ConsultationDaoimpl consultationRepo = Application_contexte.getConsultationRepository();
        Intervention_impl interventionRepo = Application_contexte.getInterventionRepository();
        Antecedent_patient_impl antecedent_patient_impl=Application_contexte.getAntecedentPatientRepository();

        List<Antecedent>liste_antecedents=new ArrayList<>();
        try {
            Patient patient = patientRepo.findById(id_patient);
            liste_antecedents=antecedent_patient_impl.find_antecedent_by_patient(patient);

            if (patient != null) {
                System.out.println("Patient trouvé : "+ patient.getNom() + " " + patient.getPrenom() + " son id est " + patient.getId());

                DossierMedical dossier = dossierRepo.find_patient(patient);
                System.out.println("l'id du dossier medical est " + dossier.getIdDossier() + " sa  Date création est  " + dossier.getDateCreation());

                System.out.println("Antécédents du patient :");
                for (Antecedent a : liste_antecedents) {
                    System.out.println("le nom de l'antecedent " + a.getNom() + " sa categorie est " + a.getCategorie());
                }

                // les Consultations
                System.out.println("Consultations :");
                for (Consultation c : consultationRepo.findByDossier(dossier.getIdDossier())) {
                    System.out.println("l'id de la consultation est " + c.getIdConsult()+ " sa date est " + c.getDateConsult() + "l'observation du medcin a etait " + c.getObservationMedecin());

                    for (Intervention i : interventionRepo.interventions_par_consultation(c)) {
                        System.out.println("l'interventin c'etait pour la dent numero" + i.getNumero_dent() + "le libelle de l'acte c'etait " + i.getActe().getLibelle());
                    }
                }
                // les ordonances
                System.out.println("ordonances :");
                for(Ordonnance ordonnance:dossierRepo.find_ordonances(dossier)){
                    System.out.println("l'id de l'ordoannce  est "+ordonnance.getIdOrd()+" a ete rediger le "+ordonnance.getDateOrd());
                }
            } else {
                System.out.println("Patient introuvable (ID = " + id_patient + ")");
            }
        } catch (Exception e) {
            System.out.println("Erreur lecture patient : " + e.getMessage());
        }
    }

    public void update(){
        Patient patient=new Patient();
        List<Antecedent> antecedents=new ArrayList<>();
        Patient_impl patient_impl=Application_contexte.getPatientRepository();
        Antecedent_impl antecedentImpl=Application_contexte.getAntecedentRepository();
        List<Antecedent> antecedents1=new ArrayList<>();
        Antecedent_patient_impl antecedent_patient_impl=Application_contexte.getAntecedentPatientRepository();
        try{
            for(int i=1;i<=3;i++){
                antecedents.add(antecedentImpl.findById(i));
            }

            patient=patient_impl.findById(patient_impl.get_last_id()-1);
            System.out.println("le patient "+patient.getNom()+" a une id de "+patient.getId());

            patient.setAntecedents(antecedents);
            patient.setNom("mehdi");
            patient_impl.update(patient);

            antecedents1=antecedent_patient_impl.find_antecedent_by_patient(patient);
            System.out.println("les nouveaux antecedents de patient sont ");

            for(Antecedent antecedent:antecedents1){
                System.out.println("le nom de l'antecedent est "+antecedent.getNom() +" son categorie est "+antecedent.getCategorie());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void delete(){
        Patient_impl patientRepo = Application_contexte.getPatientRepository();
        try {
            Patient patient = patientRepo.findById(id_patient);

            if (patient != null) {
                boolean flag = patientRepo.delete(patient);

                if (flag) {
                    System.out.println("Patient supprimé avec succès : " + patient.getNom() + " | ID : " + id_patient);
                } else {
                    System.out.println("Échec de suppression du patient (ID = " + id_patient + ")");
                }
            } else {
                System.out.println("Patient introuvable pour suppression (ID = " + id_patient + ")");
            }
        } catch (Exception e) {
            System.out.println("Erreur suppression patient : " + e.getMessage());
        }
    }


    public static void main(){
        Test_scenario_patient p=new Test_scenario_patient();
        System.out.println("\n*************Creation*************\n");
        p.creation();
        System.out.println("*************lecture*************\n");
        p.lecture();
        System.out.println("*************Update*************\n");
        p.update();
        System.out.println("*************Delete*************\n");
        p.delete();
    }
}
