package ma.prodenta.repository.test_repository;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.En.Antecedent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
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
            patient.setNom("chokr");
            patient.setPrenom("fouad");
            patient.setAdresse("Gaza");
            patient.setTelephone("06xxxxxx");
            patient.setEmail("email@domaine");
            patient.setDateNaissance(date_naissance);
            patient.setAssurance(Assurance.CNSS);
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
            System.out.println(id);
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
            System.out.println("********suppression**********\n\n");
            // flag= patient_impl.deleteById(patient2.getId());
         if(flag){
             System.out.println("le patient a ete supprimer \n");
         }
         else{
             System.out.println("erreur dans la suppression du patient\n");
         }
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
        Acte_impl acte_impl=new Acte_impl();
        List<Acte>list_antecedents=new ArrayList<>();
        try{
         System.out.println("********creation**********\n");
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
         }
            System.out.println("**********lecture**********\n");
            Acte acte1=acte_impl.findById(id-1);
            System.out.println("l'acte d'id "+id+" sa categorie est "+acte1.getCategorie()+" et son pix est "+acte1.getPrix_de_base());
            System.out.println("*********lecture de tous les elements ************\n");
            for(Acte acte2:list_antecedents=acte_impl.findAll()){
                System.out.println("l'id de l'acte est "+acte2.getId()+" et son libelle est "+acte2.getLibelle()+"\n");
            }
            System.out.println("**********modification**********\n");
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
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void test_medicament(){

    }

    public static void main(){
        test_Patient_antecedent();
    }


}
