package ma.prodenta.mvc.controllers.modules.patient;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import ma.prodenta.service.modules.patient.api.PatientService;
import ma.prodenta.service.modules.patient.baseImplementation.PatientServiceImpl;

public class Suppression_Patient_Controlleur {
    private PatientService patientService;
    private Patient_impl patient_impl;
    public Suppression_Patient_Controlleur(){
        patientService = Application_contexte.getpatientService();
        patient_impl =   Application_contexte.getPatientRepository();
    }
    public void supprimer_patient(int id){
        try{

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
