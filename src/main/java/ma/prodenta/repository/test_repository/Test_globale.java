package ma.prodenta.repository.test_repository;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.En.Antecedent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public static void main(){
        test_Patient_antecedent();
    }
}
