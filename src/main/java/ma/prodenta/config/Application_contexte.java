package ma.prodenta.config;
import ma.prodenta.entities.En.Staut_consultation;
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

public class Application_contexte {

    private static final Statut_consultation_impl statutRepository = new Statut_consultation_impl();

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
}
