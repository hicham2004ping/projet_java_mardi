package ma.prodenta.repository.test_repository;
import ma.prodenta.entities.En.*;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.medicament.fileBase_implementation.MedicamentDAOImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ma.prodenta.entities.En.Sexe_c;
import ma.prodenta.repository.modules.sexe.impl.Sexe_impl;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.repository.modules.forme.impl.Forme_impl;
import ma.prodenta.repository.modules.intervention_medcin.impl.Intervention_impl;
import ma.prodenta.repository.modules.assurance.implement.Assurance_impl;
import ma.prodenta.entities.En.DossierMedical;

public class Test_globale {
    //partie test patient

    public static void test_Patient_antecedent() {
        //pour la declaration
        LocalDate date_naissance=LocalDate.now();
        Patient patient = new Patient();
        Patient patient2 = new Patient();
        Patient_impl patient_impl=new Patient_impl();
        Antecedent_impl antecedent_impl=new Antecedent_impl();
        List<Antecedent> list_antecedent=new ArrayList<>();
        boolean flag;
        int id=0;
        try{
            //reccuparation des objets antecedents
            for(int i=1;i<=5;i++){
                list_antecedent.add(antecedent_impl.findById(i));
            }
            System.out.println("la taille de la liste est "+list_antecedent.size());
            //creation de l'objet patient
            patient.setNom("moulragouba");
            patient.setPrenom("hicham");
            patient.setAdresse("khemissat");
            patient.setTelephone("06xxxxxx");
            patient.setEmail("email@domaine");
            patient.setDateNaissance(date_naissance);
            patient.setAssurance(Assurance.CNOPS);
            patient.setSexe(Sexe.Homme);
            patient.setAntecedents(list_antecedent);
            //stockage de l'objet patient
            flag= patient_impl.create(patient);
            if(flag){
                System.out.println("le patient a ete creer avec success et son id est "+patient.getId());
                id=patient_impl.get_last_id();
            }
            else{
                System.out.println("erreur dans la creation du patient");
            }
            //pour la lecture
            System.out.println("******* lecture *********");
            System.out.println(id-1);
            patient2=patient_impl.findById(id-1);
            System.out.println("le nom du patient et son id eet "+patient2.getNom()+"et son id est  "+patient2.getId());
            System.out.println("la logneur de la liste est "+patient2.getAntecedents().size());

            //pour la modification
            System.out.println("*********modification**********\n\n");
            for(int i=6;i<=8;i++){
                list_antecedent.add(antecedent_impl.findById(i));
            }
            patient2.setAntecedents(list_antecedent);
            patient_impl.update(patient2);
            System.out.println("la logneur de la liste est "+patient2.getAntecedents().size());
            //pour la suppression
        /*    System.out.println("********suppression**********\n\n");
            // flag= patient_impl.deleteById(patient2.getId());
         if(flag){
             System.out.println("le patient a ete supprimer \n");
         }
         else{
             System.out.println("erreur dans la suppression du patient\n");
         }*/
        }
        catch(Exception e){
            System.out.println("erreur dans la creation du patient ");
            System.out.println(e.getMessage());
        }
    }

    public static void test_antecedents(){
        List<Antecedent>list_antecedents=new ArrayList<>();
        Antecedent_impl antecedent_impl=new Antecedent_impl();
        Antecedent antecedent=new Antecedent();
        int id;
        //pour la creation d'un antecedent
        antecedent.setCategorie("allergies");
        antecedent.setNom("alergie envres les ognions");
        antecedent.setNiveauRisque(NiveauRisque.Dangereux);
        try{
            System.out.println("********creation**********");
           boolean flag=antecedent_impl.create(antecedent);
            if (flag){
                System.out.println("antecedent a ete creer avec succes");
            }
            else{
                System.out.println("erreur dans la creation du patient");
            }
            //pour la lecture d'un antecedent
            System.out.println("**********lecture***********\n\n");
            id=antecedent_impl.get_last_id();
            Antecedent antecdent1=antecedent_impl.findById(id);
            System.out.println("le nom de la derniere antecedent qui a ete stocker dans la base est "+antecdent1.getNom()+" et son categorie est "+antecdent1.getCategorie()+"\n son niveau de risque est "+antecdent1.getNiveauRisque());
            //pour la modification
            System.out.println("********modification**********\n\n");

            antecdent1.setCategorie("psychologique");
            antecedent_impl.update(antecdent1);
            System.out.println("la categorie de l'antecedent1 est "+antecdent1.getCategorie());
            //suppression
            System.out.println("**************suppression**********\n\n");
            boolean flag1=antecedent_impl.delete(antecdent1);
            if(flag1){
                System.out.println("suppression avec succes de l'objet ");
            }
            else{
                System.out.println("suppression echouer");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void test_acte(){
        Acte acte=new Acte();
        Acte acte5=new Acte();
        Acte_impl acte_impl=new Acte_impl();
        List<Acte>list_antecedents=new ArrayList<>();
        try{
        /* System.out.println("********creation**********\n");
         int id=acte_impl.get_last_id();
         System.out.println("la valeur de id c'est "+id);
         acte.setCategorie("Soins Conservateurs");
         acte.setId(id);
         acte.setLibelle("Traitement de carie simple");
         acte.setPrix_de_base(1000);
        boolean flag1=acte_impl.create(acte);
         if (flag1){
             System.out.println("acte a ete creer avec succes");
         }
         else{
            System.out.println("erreur dans la creation du patient");
         }*/
           int  id1=acte_impl.get_last_id();
            System.out.println("**********lecture**********\n");
            Acte acte1=acte_impl.findById(id1-1);
            System.out.println("l'acte d'id "+id1+" sa categorie est "+acte1.getCategorie()+" et son pix est "+acte1.getPrix_de_base());
            //findbynom
            acte5=acte_impl.findbynom("Détartrage");
            System.out.println("le prix de"+acte5.getLibelle()+" est "+acte5.getPrix_de_base());

            System.out.println("*********lecture de tous les elements ************\n");
            for(Acte acte2:list_antecedents=acte_impl.findAll()){
                System.out.println("l'id de l'acte est "+acte2.getId()+" et son libelle est "+acte2.getLibelle()+"\n");
            }
          /*  System.out.println("**********modification**********\n");
            acte1.setPrix_de_base(10000);
            acte_impl.update(acte1);
            System.out.print("le prix de l'acte est "+acte1.getPrix_de_base());
            System.out.println("*************suppression**********\n");
           boolean flag=acte_impl.delete(acte1);
            if(flag) {
                System.out.println("suppression avec succes de l'objet ");
            }
            else{
                System.out.println("suppression echouer");
            }*/
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

     public static void test_medicament(){
        Forme forme=new Forme();
        Forme_impl forme_impl=new Forme_impl();
        Medicament medicament=new Medicament();
        List<Medicament>list_medicament=new ArrayList<>();
        MedicamentDAOImpl medicamentDAOImpl=new MedicamentDAOImpl();
        medicament.setNom("doliprane");
        medicament.setDescription("pour le mal de tete ");
        medicament.setLaboratoire("emsi_lab");
        medicament.setRemboursable(false);
        medicament.setPrixUnit(17.5);
        medicament.setType("simple");
        try{
            System.out.println("********creation**********\n");
            //pour creer l'objet medicament
         /*   forme=forme_impl.findbynom("comprime");
            System.out.println("l'id de la forme comprim est "+forme.getId());
            medicament.setIdForme(forme.getId());
            boolean flag=medicamentDAOImpl.create(medicament);
            if(flag){
                System.out.println("le medicament a ete bien creer");
            }
            else{
                System.out.println("erreur dans la creation de l'objet medicament ");
            }*/
            System.out.println("**********lecture**********\n");
            int id=medicamentDAOImpl.last_id();
            medicament=medicamentDAOImpl.findById(id);
            System.out.println("l'id du dernier medicament inserer dans la base de donnee est "+medicament.getIdMed()+" et son nom est "+medicament.getNom());
            //pour lire une liste
            System.out.println("**********liste_medicaments**********\n");
            list_medicament=medicamentDAOImpl.findAll();
            for(Medicament med:list_medicament){
                System.out.println("le nom du medicament est "+med.getNom()+" et son id est "+med.getIdMed());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void test_forme(){
        Forme forme=new Forme();
        Forme_impl forme_impl=new Forme_impl();
        List<Forme> liste_forme=new ArrayList<>();
        Forme forme1=new Forme();
        try{
            System.out.println("*********lecture**********\n");
            //reccuperation par nom
            forme=forme_impl.findbynom("Sirop");
            if(forme !=null){
                int id_sirop=forme.getId();
                System.out.println("l'id de sirop est "+forme.getId()+" son libelle est "+forme.getLibelle());
            }
            else{
                System.out.println("lecture echouer !!");
            }
            //recuperation par id
            Forme forme3=forme_impl.findById(2);
            System.out.println("le libelle de l'objet numero 2 est "+forme3.getLibelle());

            // lecture de tous les formes
            liste_forme=forme_impl.findAll();
            System.out.println("lecture de tous les elements depuis la table \n");
            for(Forme forme2:liste_forme){
                System.out.println("le libelle de la forme est  "+forme2.getLibelle()+" son id est "+forme2.getId());
            }

          /*  System.out.println("*********suppression**********\n");
                System.out.println("supresssion de l'objet forme d'id 1 ");
                forme1.setId(1);
                System.out.println(forme1.getId());
                boolean flag2=forme_impl.delete(forme1);
                if(flag2){
                    System.out.println("suppression avec succes de l'objet ");
                }
                else{
                    System.out.println("suppression echouer");
                }*/
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void test_intervention_medcin(){
        Intervention intervention=new Intervention();
        Intervention_impl intervention_impl=new Intervention_impl();
        Acte acte=new Acte();
        Acte_impl acte_impl=new Acte_impl();
        int id;
        try{
            System.out.println("********Intervention**********\n");
            System.out.println("********CREATION**********\n");
            acte=acte_impl.findbynom("Consultation simple");
            if(acte !=null){
                intervention.setActe(acte);
                intervention.setPrix_patient(-1);
                intervention.setNumero_dent(23);
             //boolean flag=intervention_impl.create(intervention);
            // if(flag){
              //   System.out.println("creation avec success");
            // }
             //else{
               //  System.out.println("echec lors de la creation");
             //}
             System.out.println("***********LECTURE**********\n");
             id=intervention_impl.get_last_id();
             System.out.println("la derniere id c'est "+id);
             Intervention intervention2=intervention_impl.findById(id);
             System.out.println("l'id de cette intervention est "+intervention2.getId()+" son prix est "+intervention2.getPrix_patient()+" et l'acte de cette intervention est "+intervention2.getActe().getLibelle());
             System.out.println("*************MODIFICATION**********\n");
             intervention2.setPrix_patient(190);
             intervention_impl.update(intervention2);
             System.out.println("le prix de l'intervention maintenant est "+intervention_impl.findById(intervention2.getId()).getPrix_patient());
             System.out.println("*************SUPPRESSION**********\n");
             boolean flag1=intervention_impl.deleteById(id);
             if(flag1){
                 System.out.println("suppression avec success");
             }
             else{
                 System.out.println("echec lors de la suppression");
             }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void test_assurance(){
        Assurance_c assurance=new Assurance_c();
        Assurance_impl assurance_impl=new Assurance_impl();
        List<Assurance_c> liste_assurances=new ArrayList<>();
        try{
            System.out.println("********LECTURE***********\n");
            assurance=assurance_impl.find_by_name("cops");
            System.out.println("l'id de l'assurance cnops est "+assurance.getId());
            System.out.println("\t********FINDALL***********\n");
            liste_assurances=assurance_impl.findAll();
            for(Assurance_c assurance2:liste_assurances){
                System.out.println("le libelle de l'assurance est "+assurance2.getLibelle()+" et son id est "+assurance2.getId());
            }
            System.out.println("\t********FINDBYID***********\n");
            System.out.println("le libelle de l'assurance d'id 1 c'est "+assurance_impl.findById(1).getLibelle());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void test_consultation(){
    }
    public static void test_sexe(){
        List<Sexe_c> list_sexe=new ArrayList<>();
        Sexe_c sexe=new Sexe_c();
        Sexe_impl sexe_impl=new Sexe_impl();
        try{
            list_sexe=sexe_impl.findAll();
            System.out.println("********LETURE***********\n");
            sexe=sexe_impl.findBylibelle("femme");
            if(sexe !=null){
                System.out.println("l'id du sexe femme  est "+sexe.getId());
            }
            else System.out.println("erreur dans la lecture ");
            System.out.println("*********LECTURE-LISTE***********\n");
            for(Sexe_c sexe2:list_sexe){
                System.out.println("le libelle de l'sexe est "+sexe2.getLibelle()+" et son id est "+sexe2.getId());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void test_ordonance(){
        Patient patient=new Patient();
        Patient_impl patient_impl=new Patient_impl();
        DossierMedical dossierMedical=new DossierMedical();
       /* Dossier_Medical_Impl dossierMedical_impl=new Dossier_Medical_Impl();
        Ordonnance ordonnance=new Ordonnance();
        OrdonnanceDaoImpl ordonance_impl=new OrdonnanceDaoImpl();
        int id;
        try{
            System.out.println("*******CREATION***********\n");
            patient=patient_impl.findById(33);
            dossierMedical=dossierMedical_impl.find_patient(patient);
            DossierMedical ds=dossierMedical_impl.findById(1);
            System.out.println("l'id du dossier c'est "+ds.getIdDossier());

            ordonnance.setDateOrd(LocalDate.now());
            ordonnance.setIdDossier(dossierMedical.getIdDossier());
            boolean flag=ordonance_impl.create(ordonnance);
            if(flag){
                System.out.println("creation avec succes de l'rodonance");
            }
            else{
                System.out.println("erreur lors de la creation ");
            }
        }
        catch (Exception e){
                System.out.println(e.getMessage());
        }*/
    }

    public static void test_dossier_medicale(){
        DossierMedical dossier_medical=new DossierMedical();
        Dossier_medical_impl dossier_medical_impl=new Dossier_medical_impl();
        List<DossierMedical> list_dossier_medical=new ArrayList<>();
        try{
            System.out.println("********CREATION***********\n");
            dossier_medical.setDateCreation(LocalDate.now());
            dossier_medical.setIdMedecin(1);
            dossier_medical.setIdPatient(3);
          /*  boolean flag=dossier_medical_impl.create(dossier_medical);

            if (flag) {
                System.out.println("creation avec success");
            }
            else{
                System.out.println("erreur lors de la creation");
            }*/
            System.out.println("**********LECTURE***********\n");
            int id=dossier_medical_impl.get_last_id();
            System.out.println("la dernieer dossier medicale inserer a "+id+" comme id");
            DossierMedical dossier=dossier_medical_impl.findById(id);
            System.out.println("le dossier d'id "+dossier.getIdDossier()+" a ete creer dans le "+dossier.getDateCreation());
            System.out.println("************LECTURE LIST***********\n");
            list_dossier_medical=dossier_medical_impl.findAll();
            for(DossierMedical ds:list_dossier_medical){
                System.out.println("l'id du dossier est "+ds.getIdDossier());
            }
            System.out.println("**********SUPRESSION**********\n");
            System.out.println("on va supprimer le dernier element d'id "+id);
            boolean flag2=dossier_medical_impl.deleteById(id);
            if(flag2){
                System.out.println("suppresion avec success");
            }
            else{
                System.out.println("echec lors de la suppression");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(){
        test_dossier_medicale();
    }
}
