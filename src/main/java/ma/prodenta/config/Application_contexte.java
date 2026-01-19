package ma.prodenta.config;
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
import ma.prodenta.repository.modules.assurance.implement.Assurance_impl;
import ma.prodenta.repository.modules.sexe.impl.Sexe_impl;
import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
import ma.prodenta.service.modules.antecedent.impl.Antecedent_Service_ServiceImpl;
import ma.prodenta.mvc.controllers.modules.antecedent.impl.Antecedent_Controlleur_Impl;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.service.modules.agenda.impl.AgendaServiceImpl;
import ma.prodenta.service.modules.dashboard.impl.DashboardServiceImpl;
import ma.prodenta.service.modules.auth.impl.AuthServiceImpl;
import ma.prodenta.service.modules.usermanager.impl.UserManagerServiceImpl;
import ma.prodenta.mvc.controllers.modules.auth.impl.AuthControlleur_Impl;
import ma.prodenta.mvc.controllers.modules.userManager.impl.UserManagerControlleur_Impl;
import ma.prodenta.mvc.controllers.modules.facture.impl.FactureController;
import ma.prodenta.mvc.controllers.modules.ordonnance.impl.OrdonnanceController;
import ma.prodenta.mvc.controllers.modules.caisse.impl.CaisseController;
import ma.prodenta.repository.modules.agenda.implementation.AgendaRepositoryImpl;
import ma.prodenta.repository.modules.Dashboard.implementation.DashboardRepositoryImpl;
import ma.prodenta.mvc.ui.auth.LoginFrame;
import ma.prodenta.repository.modules.fieldattente.FileAttenteDaoImpl;
import ma.prodenta.service.modules.FileAttenteService;

public class Application_contexte {

    public static final PatientServiceImpl patientServiceImpl = new PatientServiceImpl();
    public static final Ordonance_Service_impl ordonanceServiceImpl = new Ordonance_Service_impl();
    public static final DossierMedicalServiceImpl dossierMedicalServiceImpl = new DossierMedicalServiceImpl();
    public static final Consultation_service_impl consultationServiceImpl = new Consultation_service_impl();
    public static final Intervention_Service_impl interventionServiceImpl = new Intervention_Service_impl();
    public static final Acte_Service_impl acteServiceImpl = new Acte_Service_impl();
    public  static final Prescription_impl prescriptionServiceImpl = new Prescription_impl();
    public static final Statut_consultation_impl statutRepository = new Statut_consultation_impl();
    public static final RDVDAOImpl rendez_vous= new RDVDAOImpl();
    private static final Sexe_impl sexe_impl = new Sexe_impl();
    private static final Assurance_impl assurance_impl=new Assurance_impl();
    private static final Patient_Controlleur patientControlleur=new Patient_Controlleur();
    private static final Antecedent_Service_ServiceImpl antecedent_service=new Antecedent_Service_ServiceImpl();
    private static final Antecedent_Controlleur_Impl antecedent_controlleur=new Antecedent_Controlleur_Impl();
    private static final DossierMedicalController dossierMedicalController=new DossierMedicalController();
    private static final DossierMedicalServiceImpl dossierMedicalService=new DossierMedicalServiceImpl();

    private static final AgendaServiceImpl agendaServiceImpl = new AgendaServiceImpl();
    private static final DashboardServiceImpl dashboardServiceImpl = new DashboardServiceImpl();
    private static final AuthServiceImpl authServiceImpl = new AuthServiceImpl();
    private static final UserManagerServiceImpl userManagerServiceImpl = new UserManagerServiceImpl();
    private static final AuthControlleur_Impl authControlleur = new AuthControlleur_Impl();
    private static final UserManagerControlleur_Impl userManagerControlleur = new UserManagerControlleur_Impl();

    private static final Patient_impl patientRepository = new Patient_impl();

    private static final Antecedent_impl antecedentRepository = new Antecedent_impl();

    private static final Antecedent_patient_impl antecedentPatientRepository = new Antecedent_patient_impl();

    private static final Dossier_medical_impl dossierMedicalRepository = new Dossier_medical_impl();

    private static final ConsultationDaoimpl consultationRepository = new ConsultationDaoimpl();

    private static final OrdonnanceDaoImpl ordonnanceRepository = new OrdonnanceDaoImpl();

    private static final MedicamentDAOImpl medicamentRepository = new MedicamentDAOImpl();

    private static final Prescription_impl prescriptionRepository = new Prescription_impl();

    private static final Acte_impl acteRepository = new Acte_impl();

    private static final Intervention_impl interventionRepository = new Intervention_impl();
    private static final CertificatDaoimpl certificatRepository = new CertificatDaoimpl();

    private static final FileAttenteDaoImpl fileAttenteRepository = new FileAttenteDaoImpl();
    private static final FileAttenteService fileAttenteService = new FileAttenteService();

    private static final AgendaRepositoryImpl agendaRepository = new AgendaRepositoryImpl(null);
    private static final DashboardRepositoryImpl dashboardRepository = new DashboardRepositoryImpl(null);

    private static final FactureController factureController = new FactureController();
    private static final OrdonnanceController ordonnanceController = new OrdonnanceController();
    private static final CaisseController caisseController = new CaisseController();

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

    public static Ordonance_Service_impl getordonnanceService(){
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

    public static FileAttenteDaoImpl getFileAttenteRepository(){
        return fileAttenteRepository;
    }

    public static FileAttenteService getFileAttenteService(){
        return fileAttenteService;
    }

    public static Assurance_impl getAssurance_impl(){
        return assurance_impl;
    }
    public static Sexe_impl getSexe_impl(){
        return sexe_impl;
    }
    public static Patient_Controlleur getPatientControlleur(){
        return patientControlleur;
    }
    public static Antecedent_Service_ServiceImpl getAntecedent_Service(){
        return antecedent_service;
    }
    public static Antecedent_Controlleur_Impl getAntecedent_controlleur(){
        return antecedent_controlleur;
    }
    public static DossierMedicalController getDossierMedicalController(){
        return dossierMedicalController;
    }
    public static DossierMedicalServiceImpl getDossierMedicalServiceImpl(){
        return dossierMedicalServiceImpl;
    }

    public static AgendaServiceImpl getAgendaServiceImpl(){
        return agendaServiceImpl;
    }

    public static DashboardServiceImpl getDashboardServiceImpl(){
        return dashboardServiceImpl;
    }

    public static AuthServiceImpl getAuthServiceImpl(){
        return authServiceImpl;
    }

    public static UserManagerServiceImpl getUserManagerServiceImpl(){
        return userManagerServiceImpl;
    }

    public static AuthControlleur_Impl getAuthControlleur(){
        return authControlleur;
    }

    public static UserManagerControlleur_Impl getUserManagerControlleur(){
        return userManagerControlleur;
    }

    public static AgendaRepositoryImpl getAgendaRepository(){
        return agendaRepository;
    }

    public static DashboardRepositoryImpl getDashboardRepository(){
        return dashboardRepository;
    }

    public static FactureController getFactureController() {
        return factureController;
    }

    public static OrdonnanceController getOrdonnanceController() {
        return ordonnanceController;
    }

    public static CaisseController getCaisseController() {
        return caisseController;
    }

    // Added new fields for LoginFrame
    private static final LoginFrame loginFrame = new LoginFrame();

    public static LoginFrame getLoginFrame() {
        return loginFrame;
    }
}
