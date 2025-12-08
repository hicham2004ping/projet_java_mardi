package ma.prodenta.service.modules.patient.baseImplementation;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.service.modules.patient.api.PatientService;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class PatientServiceImpl implements PatientService {

    @Override
    public boolean creation(Patient p) throws Exception {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.create(p);
    }

    @Override
    public boolean creation_dossier_medical(Patient p,int id_medecin) throws SQLException, IOException {
        Dossier_medical_impl dossier_medical_impl = new Dossier_medical_impl();
        DossierMedical dossier=new DossierMedical();
        dossier.setIdPatient(p.getId());
        dossier.setIdMedecin(id_medecin);
        dossier.setDateCreation(LocalDate.now());
        return dossier_medical_impl.create(dossier);
    }

    @Override
    public void update_patient(Patient p) throws SQLException {
        Patient_impl patient_impl = new Patient_impl();
        patient_impl.update(p);
    }

    @Override
    public boolean delete_patient(Patient p) throws SQLException, IOException {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.delete(p);
    }

    @Override
    public boolean delete_patient_by_id(Integer id) throws SQLException, IOException {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.deleteById(id);
    }

    @Override
    public Patient find_by_id(Integer id) throws Exception {
        Patient_impl patient_impl = new Patient_impl();
        return  patient_impl.findById(id);
    }

    @Override
    public Patient find_by_email(String email) {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.findByEmail(email);
    }

    @Override
    public Optional<Patient> find_by_telephone(String tel) {
        return Optional.empty();
    }

    @Override
    public List<Patient> search_by_nom_prenom(String keyword) {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.searchByNomPrenom(keyword);
    }

    @Override
    public boolean exists_by_id(long id) {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.existsById(id);
    }

    @Override
    public long count() {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.count();
    }

    @Override
    public List<Patient> find_all() throws Exception {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.findAll();
    }

    @Override
    public List<Patient> find_page(int limit, int offset) {
        return List.of();
    }

    @Override
    public boolean add_antecedent_to_patient(int patientId, int antecedentId) throws Exception {
        Patient_impl patient_impl = new Patient_impl();
        Antecedent_patient_impl antecedent_impl = new Antecedent_patient_impl();
        Antecedent_impl antecedentImpl=new Antecedent_impl();
        Antecedent antecedent=new Antecedent();
        antecedent=antecedentImpl.findById(antecedentId);
        Patient p= new Patient();
        p=patient_impl.findById(patientId);
        return antecedent_impl.ajouter_antecedent_patient(p,antecedent);
    }

    @Override
    public boolean remove_antecedent_from_patient(int patientId, int antecedentId) throws Exception {
        Patient_impl patient_impl = new Patient_impl();
        Antecedent_patient_impl antecedent_impl = new Antecedent_patient_impl();
        Antecedent_impl antecedentImpl=new Antecedent_impl();
        Antecedent antecedent=new Antecedent();
        antecedent=antecedentImpl.findById(antecedentId);
        Patient p= new Patient();
        p=patient_impl.findById(patientId);
        return antecedent_impl.supprimer_antecedent_patient(p,antecedent);

    }

    @Override
    public boolean remove_all_antecedents_from_patient(int patientId) throws Exception {
        Patient_impl patient_impl = new Patient_impl();
        Antecedent_patient_impl antecedent_impl = new Antecedent_patient_impl();
        Patient p= new Patient();
        p=patient_impl.findById(patientId);
        return antecedent_impl.supprimer_antecedent_par_patient(p);
    }

    @Override
    public List<Antecedent> get_antecedents_of_patient(int patientId) throws Exception {
        Patient_impl patient_impl = new Patient_impl();
        Antecedent_patient_impl antecedent_impl = new Antecedent_patient_impl();
        Patient p= new Patient();
        p=patient_impl.findById(patientId);
        return antecedent_impl.find_antecedent_by_patient(p);
    }

    @Override
    public List<Patient> get_patients_by_antecedent(int antecedentId) throws Exception {
        Antecedent_patient_impl antecedent_impl = new Antecedent_patient_impl();
        Antecedent_impl antecedentImpl=new Antecedent_impl();
        Antecedent antecedent=new Antecedent();
        antecedent=antecedentImpl.findById(antecedentId);
        Patient p= new Patient();
        return antecedent_impl.find_patients_by_antecedent(antecedent);
    }

    @Override
    public int get_last_id() throws SQLException, IOException {
        Patient_impl patient_impl = new Patient_impl();
        return patient_impl.get_last_id();
    }

    @Override
    public Optional<Antecedent> find_by_nom(String nom) {
        return Optional.empty();
    }

    public static void  main(){
        Patient_impl patient_impl = new Patient_impl();
        Dossier_medical_impl dossier_medical_impl = new Dossier_medical_impl();
        PatientServiceImpl patient_service = new PatientServiceImpl();
        try{
            //Patient p=patient_impl.findById(33);
          //  boolean flag=patient_service.creation_dossier_medical(p,1);
            //    boolean flag=patient_service.creation(p);
            //p.setNom("karaki");
            //p.setPrenom("ali");
            //Patient p2=patient_impl.findById(p.getId());
            //System.out.println("le nouveau nom du patient p "+p.getNom()+" son prenom est "+p.getPrenom());
           // boolean flag=patient_service.delete_patient_by_id(13);
            Patient p=patient_service.find_by_id(16);
            System.out.println("le nom du patient 16 est "+p.getNom()+" son prenom est "+p.getPrenom());
            Patient p2=patient_service.find_by_email("email@domaine");
            System.out.println("le nom du patient p2 est "+p2.getNom()+" son prenom est "+p2.getPrenom());
/*            List<Patient> list=patient_service.search_by_nom_prenom("safiyeddine");
            for(Patient p3:list){
                System.out.println("l'id du patient est "+p3.getId());
            }*/
           // boolean flag=patient_service.exists_by_id(1);
            long nombre=patient_service.count();
            System.out.println("le nombre des patients dans la table est "+nombre);
          //  boolean flag=patient_service.add_antecedent_to_patient(1,10);
           // boolean flag=patient_service.remove_antecedent_from_patient(1,10);
          //  boolean flag=patient_service.remove_all_antecedents_from_patient(34);
            List<Antecedent> list_antecedent=new ArrayList<>();
            list_antecedent=patient_service.get_antecedents_of_patient(15);
            for(Antecedent a:list_antecedent){
                System.out.println("l'id de l'antecedent est "+a.getIdAntecedent());
            }
            List<Patient> list_patient=new ArrayList<>();
            list_patient=patient_service.get_patients_by_antecedent(1);
            for(Patient a:list_patient){
                System.out.println("le nom du patient qui a l'antecedent 1 est "+a.getNom());
            }
            int id=patient_service.get_last_id();
            System.out.println("la derniere id dans la table patient est "+(id-1));
           /* if(flag){
                System.out.println("succes");
            }
            else System.out.println("erreur ");*/
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}