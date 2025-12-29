package ma.prodenta.service.modules.patient.baseImplementation;
import ma.prodenta.common.exceptions.*;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.service.modules.patient.api.PatientService;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.service.common.validateur.email.validateur_email;
import ma.prodenta.common.validators.Date_naissance_Validator;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PatientServiceImpl implements PatientService {
    @Override
    public Patient find_by_id(int id) {
        if (id<=0){
            throw new ArgumentException("l'id du patient n'est pas valide");
        }
        Patient patient=new Patient();
        Patient_impl patientDao=Application_contexte.getPatientRepository();
        try{
            patient=patientDao.findById(id);
            return patient;
        } catch (Exception e) {
            System.out.println("erreur lors de la lecture du patient ");
            throw new ErreurLectureException("impossible de trouver le patient");
        }
    }

    @Override
    public void creation(Patient p, int idMedecin) throws ErreurCreationException, SQLException, IOException,EmailInvalideException,EmailExisteException {

        if (p == null)
            throw new ArgumentException("Patient invalide");

        if (p.getNom() == null || p.getNom().isBlank())
            throw new ArgumentException("Nom du patient obligatoire");

        if (p.getEmail() == null || !validateur_email.is_valid(p.getEmail()))
            throw new EmailInvalideException("Email invalide");

        if (p.getTelephone() == null || p.getTelephone().isBlank())
            throw new ArgumentException("Téléphone obligatoire");

        if(p.getDateNaissance().isAfter(LocalDate.now())){
            throw new Date_Naissance_Exception("erreur lors de la saisie de la date du naissance du patient");
        }

        Patient_impl patientRepo = Application_contexte.getPatientRepository();
        Antecedent_patient_impl antecedentRepo = Application_contexte.getAntecedentPatientRepository();
        Dossier_medical_impl dossierRepo = Application_contexte.getDossierMedicalRepository();

        //verifier l'existance d'un patient avec le meme email
        if(patientRepo.findByEmail(p.getEmail())!=null){
            throw new EmailExisteException("un patient existe deja avec cette email");
        }

        if (!patientRepo.create(p))
            throw new ErreurCreationException("Impossible de créer le patient");

        System.out.println("Patient créé avec succès, ID = " + p.getId());


        System.out.println("Antécédents ajoutés avec succès");

        DossierMedical dossier = new DossierMedical();
        dossier.setIdPatient(p.getId());
        dossier.setIdMedecin(idMedecin);
        dossier.setDateCreation(LocalDate.now());

        if (!dossierRepo.create(dossier))
            throw new ErreurCreationException("Impossible de créer le dossier médical");

        System.out.println("Dossier médical créé avec succès son id est " + dossier.getIdDossier());
    }

    @Override
    public Patient find_by_email(String email) throws Exception {
        Patient_impl repo = Application_contexte.getPatientRepository();
        Patient p = repo.findByEmail(email);

        if (p == null)
            throw new ErreurLectureException("Aucun patient avec cet email");

        return p;
    }

    @Override
    public List<Patient> search_by_nom_prenom(String keyword) throws Exception {
        Patient_impl repo = Application_contexte.getPatientRepository();
        List<Patient> patients = repo.searchByNomPrenom(keyword);

        if (patients == null || patients.isEmpty())
            throw new ErreurLectureException("Aucun patient trouvé");

        return patients;
    }

    @Override
    public long count() {
        Patient_impl repo = Application_contexte.getPatientRepository();
        return repo.count();

    }

    @Override
    public List<Patient> find_all() throws Exception {
        Patient_impl repo = Application_contexte.getPatientRepository();
        List<Patient> patients = repo.findAll();

        if (patients == null || patients.isEmpty())
            throw new ErreurLectureException("Aucun patient dans la base");

        return patients;
    }

    @Override
    public void ajouter_antecedent_to_patient(int patientId, int antecedentId) throws Exception
    {
        Patient_impl patientRepo = new Patient_impl();
        Antecedent_impl antecedentRepo = new Antecedent_impl();
        Antecedent_patient_impl linkRepo = new Antecedent_patient_impl();

        Patient p = patientRepo.findById(patientId);
        Antecedent a = antecedentRepo.findById(antecedentId);

        if (p == null || a == null)
            throw new ErreurLectureException("Patient ou antécédent introuvable");

        if (!linkRepo.ajouter_antecedent_patient(p, a))
            throw new ErreurCreationException("Impossible d’ajouter l’antécédent");

        System.out.println("Antécédent ajouté avec succès");
    }

    @Override
    public void supprimer_antecedent_from_patient(int patientId, int antecedentId) throws Exception {

        Patient_impl patientRepo = new Patient_impl();
        Antecedent_impl antecedentRepo = new Antecedent_impl();
        Antecedent_patient_impl linkRepo = new Antecedent_patient_impl();

        Patient p = patientRepo.findById(patientId);
        Antecedent a = antecedentRepo.findById(antecedentId);

        if (p == null || a == null)
            throw new Exception("Patient ou antécédent introuvable");

        if (!linkRepo.supprimer_antecedent_patient(p, a))
            throw new Exception("Impossible de supprimer l’antécédent");

        System.out.println("Antécédent supprimé avec succès");
    }

    @Override
    public void remove_all_antecedents_from_patient(int patientId) throws Exception {

        Patient_impl patientRepo = new Patient_impl();
        Antecedent_patient_impl linkRepo = new Antecedent_patient_impl();

        Patient p = patientRepo.findById(patientId);

        if (p == null)
            throw new ErreurLectureException("patient introuvale ");
        if (!linkRepo.supprimer_antecedent_par_patient(p))
            throw new Exception("Impossible de supprimer les antécédents");

        System.out.println("Tous les antécédents ont été supprimés pour le patient " + p.getNom()+" "+p.getPrenom());
    }


    @Override
    public List<Antecedent> get_antecedents_of_patient(int patientId) throws Exception {

        Patient_impl patientRepo = new Patient_impl();
        Antecedent_patient_impl linkRepo = new Antecedent_patient_impl();

        Patient p = patientRepo.findById(patientId);

        if (p == null)
            throw new Exception("Patient introuvable");

        List<Antecedent> antecedents = linkRepo.find_antecedent_by_patient(p);
        if (antecedents == null || antecedents.isEmpty()){
            throw new Exception("ca patient n'a aucun antecedent");
        }
        return antecedents;
    }

    @Override
    public List<Patient> get_patients_by_antecedent(int antecedentId) throws Exception {

        Antecedent_impl antecedentRepo =Application_contexte.getAntecedentRepository();
        Antecedent_patient_impl linkRepo =Application_contexte.getAntecedentPatientRepository();

        Antecedent a = antecedentRepo.findById(antecedentId);

        if (a == null)
            throw new ErreurLectureException("Antecedent introuvable");

        List<Patient> patients = linkRepo.find_patients_by_antecedent(a);
        if (patients.isEmpty()){
            throw new  Exception("aucun patient ne souffre d'un antecedent");
        }
        return patients;
    }

    @Override
    public int get_last_id() throws SQLException, IOException {
        Patient_impl repo = Application_contexte.getPatientRepository();
        int id = repo.get_last_id();
        return id;
    }

    @Override
    public void delete_by_id(int id) throws Exception {
        Patient_impl repo=Application_contexte.getPatientRepository();
        if(id<=0){
            throw new IllegalArgumentException("l'id du patient est non valide ");
        }
        boolean flag=repo.deleteById(id);
        if(!flag){
            throw new ErreurSuppressionException("le patient n'a pas pu etre supprimer");
        }
        System.out.println("le patient a ete supprimer avec success");
    }
}
