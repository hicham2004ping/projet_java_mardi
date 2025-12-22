package ma.prodenta.config;
import ma.prodenta.entities.En.Staut_consultation;
import ma.prodenta.repository.modules.RendezVous.fileBase_implementation.RDVDAOImpl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.repository.modules.ordonnance.impl.OrdonnanceDaoImpl;
import ma.prodenta.repository.modules.medicament.fileBase_implementation.MedicamentDAOImpl;
import ma.prodenta.repository.modules.prescription.impl.Prescription_impl;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.repository.modules.intervention_medcin.impl.Intervention_impl;
import ma.prodenta.repository.modules.statut_consultation.impl.Statut_consultation_impl;
import ma.prodenta.service.modules.actes.impl.Acte_Service_impl;
import ma.prodenta.service.modules.consultation.impl.Consultation_service_impl;
import ma.prodenta.service.modules.dossierMedical.impl.DossierMedicalServiceImpl;
import ma.prodenta.service.modules.intervention.impl.Intervention_Service_impl;
import ma.prodenta.service.modules.ordonnance.impl.Ordonance_Service_impl;
import ma.prodenta.service.modules.patient.baseImplementation.PatientServiceImpl;
import ma.prodenta.repository.modules.certificat.impl.CertificatDaoimpl;

public class Application_contexte {
    public static final PatientServiceImpl patientServiceImpl = new PatientServiceImpl();
    public static final Ordonance_Service_impl ordonanceServiceImpl = new Ordonance_Service_impl();
    public static final DossierMedicalServiceImpl dossierMedicalServiceImpl = new DossierMedicalServiceImpl();
    public static final Consultation_service_impl consultationServiceImpl = new Consultation_service_impl();
    public static final Intervention_Service_impl interventionServiceImpl = new Intervention_Service_impl();
    public static final Acte_Service_impl acteServiceImpl = new Acte_Service_impl();

    private static final Statut_consultation_impl statutRepository =
            new Statut_consultation_impl();
    private static final RDVDAOImpl rendez_vous=
            new RDVDAOImpl();

    private static final Patient_impl patientRepository =
            new Patient_impl();

    private static final Antecedent_impl antecedentRepository =
            new Antecedent_impl();

    private static final Antecedent_patient_impl antecedentPatientRepository =
            new Antecedent_patient_impl();

    private static final Dossier_medical_impl dossierMedicalRepository =
            new Dossier_medical_impl();

    private static final ConsultationDaoimpl consultationRepository =
            new ConsultationDaoimpl();

    private static final OrdonnanceDaoImpl ordonnanceRepository =
            new OrdonnanceDaoImpl();

    private static final MedicamentDAOImpl medicamentRepository =
            new MedicamentDAOImpl();

    private static final Prescription_impl prescriptionRepository =
            new Prescription_impl();

    private static final Acte_impl acteRepository =
            new Acte_impl();

    private static final Intervention_impl interventionRepository =
            new Intervention_impl();
    private static final CertificatDaoimpl certificatRepository =
            new CertificatDaoimpl();

    public static Patient_impl getPatientRepository() {
        return patientRepository;
    }

    public static Antecedent_impl getAntecedentRepository() {
        return antecedentRepository;
    }

    public static Antecedent_patient_impl getAntecedentPatientRepository() {
        return antecedentPatientRepository;
    }

    public static Dossier_medical_impl getDossierMedicalRepository() {
        return dossierMedicalRepository;
    }

    public static ConsultationDaoimpl getConsultationRepository() {
        return consultationRepository;
    }

    public static OrdonnanceDaoImpl getOrdonnanceRepository() {
        return ordonnanceRepository;
    }

    public static MedicamentDAOImpl getMedicamentRepository() {
        return medicamentRepository;
    }

    public static Prescription_impl getPrescriptionRepository() {
        return prescriptionRepository;
    }

    public static Acte_impl getActeRepository() {
        return acteRepository;
    }

    public static Intervention_impl getInterventionRepository() {
        return interventionRepository;
    }
    public static Statut_consultation_impl getStatutRepository() {
        return statutRepository;
    }

    public static PatientServiceImpl getpatientService(){
        return patientServiceImpl;
    }
    public static DossierMedicalServiceImpl getdossierMedicalService(){
        return dossierMedicalServiceImpl;
    }

    public static Consultation_service_impl getconsultationService(){
        return consultationServiceImpl;
    }

    public static Ordonance_Service_impl getordonanceService(){
        return ordonanceServiceImpl;
    }

    public static Intervention_Service_impl getinterventionService(){
        return interventionServiceImpl;
    }

    public static Acte_Service_impl getacteService(){
        return acteServiceImpl;
    }
    public static RDVDAOImpl getRDVRepository(){
        return rendez_vous;
    }

    public static CertificatDaoimpl getCertificatRepository(){
        return certificatRepository;
    }
}
