package ma.prodenta.repository.test_repository;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.repository.modules.medicament.fileBase_implementation.MedicamentDAOImpl;
import ma.prodenta.repository.modules.prescription.impl.Prescription_impl;
import ma.prodenta.repository.modules.ordonnance.impl.OrdonnanceDaoImpl;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.repository.modules.ordonnance.impl.OrdonnanceDaoImpl;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.repository.modules.dossierMedical.implementation.Dossier_medical_impl;
import java.util.ArrayList;
import java.util.List;

public class Test_Scenario_Ordonance {
    public void Creation_ordonance(){
        MedicamentDAOImpl medicamentDAOImpl= Application_contexte.getMedicamentRepository();
        Prescription_impl prescription_impl=Application_contexte.getPrescriptionRepository();
        OrdonnanceDaoImpl ordonnanceDaoImpl=Application_contexte.getOrdonnanceRepository();
        Patient p=new Patient();
        Patient_impl patient_impl=Application_contexte.getPatientRepository();
        Ordonnance ordonnance=new Ordonnance();
        List<Medicament> medicaments=new ArrayList<>();

    }
    public static void main(){

    }
}
